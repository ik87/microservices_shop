package ru.ik87.microservices.demo_shop.payment.kafka.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ru.ik87.microservices.demo_shop.payment.persistence.model.*;
import ru.ik87.microservices.demo_shop.payment.persistence.repository.PaymentRepository;
import ru.ik87.microservices.demo_shop.payment.persistence.repository.TempRepository;

import java.util.Optional;
import java.util.function.Consumer;

/**
 *  Controller that receive data from services:
 *   service    *     type
 *  **************************
 *  Order       *   model data
 *  Customer    *   model data
 *  Delivery    *   model data
 *  ***************************
 * Logic:
 * receive data -> unite in temporary table -> save bunch in payment table
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 20.12.2020
 */
@Controller
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final PaymentRepository repository;

    private final TempRepository tempRepository;


    public Receiver(PaymentRepository repository, TempRepository tempRepository) {
        this.repository = repository;
        this.tempRepository = tempRepository;
    }

    @KafkaListener(topics = "${kakfa.consumer.topic.order}")
    public void receiveOrder(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String orderJson) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' order = '{}'", clientId, orderJson);
        //find clientId with state 'NEW' and orderId null or create new customer
        Order order = new ObjectMapper().readValue(orderJson, Order.class);
        uniteDataReceiver(clientId, temp -> temp.setOrder(order));

    }

    @KafkaListener(topics = "${kakfa.consumer.topic.customer}")
    public void receiveCustomer(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String customerJson) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' customer = '{}'", clientId, customerJson);
        //find by clientId with state 'NEW' and orderId null or create new customer
        Customer customer = new ObjectMapper().readValue(customerJson, Customer.class);
        uniteDataReceiver(clientId, temp -> temp.setCustomer(customer));
    }

    @KafkaListener(topics = "${kafka.consumer.topic.delivery}")
    public void receiveDelivery(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String deliveryJson) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' delivery = '{}'", clientId, deliveryJson);
        //find by clientId with state 'NEW' and orderId null or create new customer
        Delivery delivery = new ObjectMapper().readValue(deliveryJson, Delivery.class);
        uniteDataReceiver(clientId, temp -> temp.setDelivery(delivery));
    }



    /** Async, Idempotence.
     *  Unite data by client id that was received from services:
     *  Customer
     *  Order
     *  Delivery
     *  and save them to temporary table:
     *  "TEMP"
     *  when the row in table will be full, then save them to table:
     *  "PAYMENTS"
     *  and remove row, in temporary table
     *
     * @param clientId that define table's row
     * @param consumer universe receiver method
     * @return payment model
     */
    private void uniteDataReceiver(String clientId, Consumer<Temp> consumer) {
        Optional<Temp> temp = tempRepository.findById(Long.valueOf(clientId));
        Payment payment = null;
        if(temp.isEmpty()) {
            temp = Optional.of(new Temp());
            temp.get().setClientId(Long.valueOf(clientId));
            consumer.accept(temp.get());
        } else {
            consumer.accept(temp.get());
        }
        if(temp.get().isReady()) {
            payment = templateToPayment(temp.get());
            payment.setStatus(PaymentStatus.PENDING);
            repository.save(payment);
            tempRepository.delete(temp.get());
        } else {
            tempRepository.save(temp.get());
        }
        LOGGER.info("temp = '{}'", temp.get());
    }

    private Payment templateToPayment(Temp temp) {
        Payment payment = new Payment();
        payment.setClientId(temp.getClientId());
        payment.setCustomer(temp.getCustomer());
        payment.setDelivery(temp.getDelivery());
        payment.setOrder(temp.getOrder());
        return payment;
    }

}
