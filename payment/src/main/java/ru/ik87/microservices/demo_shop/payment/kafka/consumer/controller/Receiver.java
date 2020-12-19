package ru.ik87.microservices.demo_shop.payment.kafka.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        Payment payment = saveToTemporaryTable(clientId, temp -> temp.setOrder(order));
        if(payment != null) {
            repository.save(payment);
            LOGGER.info("payment = '{}'", payment);
        }
    }


    @KafkaListener(topics = "${kakfa.consumer.topic.customer}")
    public void receiveCustomer(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String customerJson) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' customer = '{}'", clientId, customerJson);
        //find by clientId with state 'NEW' and orderId null or create new customer
        Customer customer = new ObjectMapper().readValue(customerJson, Customer.class);
        Payment payment = saveToTemporaryTable(clientId, temp -> temp.setCustomer(customer));
        if(payment != null) {
            repository.save(payment);
            LOGGER.info("payment = '{}'", payment);
        }
    }

    @KafkaListener(topics = "${kafka.consumer.topic.delivery}")
    public void receiveDelivery(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String deliveryJson) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' delivery = '{}'", clientId, deliveryJson);
        //find by clientId with state 'NEW' and orderId null or create new customer
        Delivery delivery = new ObjectMapper().readValue(deliveryJson, Delivery.class);
        Payment payment = saveToTemporaryTable(clientId, temp -> temp.setDelivery(delivery));
        if(payment != null) {
            repository.save(payment);
            LOGGER.info("payment = '{}'", payment);
        }
    }


    //save to temporary db, check if all not null then save to delivery table
    private Payment saveToTemporaryTable(String clientId, Consumer<Temp> templateConsumer) {
        Optional<Temp> temp = tempRepository.findById(Long.valueOf(clientId));
        Payment payment = null;
        if(temp.isEmpty()) {
            temp = Optional.of(new Temp());
            temp.get().setClientId(Long.valueOf(clientId));
            templateConsumer.accept(temp.get());
        } else {
            templateConsumer.accept(temp.get());
        }
        if(temp.get().isReady()) {
            payment = templateToPayment(temp.get());
            payment.setStatus(PaymentStatus.PENDING);
            tempRepository.delete(temp.get());
        } else {
            tempRepository.save(temp.get());
        }
        LOGGER.info("temp = '{}'", temp.get());
        return payment;
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
