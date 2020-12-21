package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ru.ik87.microservices.demo_shop.delivery.kafka.dto.DeliveryDTO;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.*;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.DeliveryRepository;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.TempRepository;
import ru.ik87.microservices.demo_shop.delivery.service.DeliveryService;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Controller that receive data from services:
 *  service    *     type
 *  **************************
 *  Order       *   model data
 *  Customer    *   model data
 *  Payment     *   command
 *  ***************************
 *  Also Send data to delivery topic
 *
 *  Logic:
 *  receive data -> unite in temporary table -> save bunch in delivery table
 **
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 20.12.2020
 */
@Controller
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final DeliveryRepository repository;

    private final TempRepository tempRepository;

    @Value("${kafka.producer.topic.delivery}")
    String deliveryTopic;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Receiver(DeliveryRepository repository, TempRepository tempRepository) {
        this.tempRepository = tempRepository;
        this.repository = repository;
    }

    @KafkaListener(topics = "${kakfa.consumer.topic.order}")
    public void receiveOrder(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String orderJson) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' order = '{}'", clientId, orderJson);
        Order order = new ObjectMapper().readValue(orderJson, Order.class);
        //save to temporary table, check if all not null then save to delivery table
        unitDataAndSendToDeliveryTopic(clientId, temp -> temp.setOrder(order));
    }

    @KafkaListener(topics = "${kakfa.consumer.topic.customer}")
    public void receiveCustomer(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String customerJson) throws JsonProcessingException {
          ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("client_id ='{}' customer = '{}'", clientId, customerJson);
        Customer customer = objectMapper.readValue(customerJson, Customer.class);
        //save to temporary table, check if all not null then save to delivery table
        unitDataAndSendToDeliveryTopic(clientId, temp -> temp.setCustomer(customer));

    }

    /**
     * Idempotence.
     * After successful paid, receive delivery id from service:
     * Payment
     * send order to customer
     * @param deliveryId
     */
    @KafkaListener(topics = "${kakfa.consumer.topic.payment}")
    public void receivePayment(Long deliveryId)  {
        Optional<Delivery> delivery = repository.findById(deliveryId);
        if (delivery.isPresent()) {
            if (delivery.get().getStatus() == DeliveryStatus.PENDING) {
                delivery.get().setStatus(DeliveryStatus.SEND);
                repository.save(delivery.get());
            } else {
                LOGGER.info("delivery have been sent = '{}' ", delivery);
            }
        }
        LOGGER.info("delivery = '{}'", delivery);
    }


    /**
     * Idempotence.
     * Receive date from services:
     * Customer
     * Order
     * unite them in {@link #uniteDataReceiver(String, Consumer)}.
     * calculate and set delivery cost
     * and send to:
     * Delivery topic
     *
     * @param clientId that define table's row
     * @param consumer universe receiver method
     * @throws JsonProcessingException
     */
    private void unitDataAndSendToDeliveryTopic(String clientId, Consumer<Temp> consumer) throws JsonProcessingException {
        Delivery delivery = uniteDataReceiver(clientId, consumer);
        if (delivery != null) {
            delivery.setPrice(deliveryService.calcPrice(delivery));
            repository.save(delivery);
            ObjectMapper objectMapper = new ObjectMapper();
            DeliveryDTO deliveryDTO = objectMapper.convertValue(delivery, DeliveryDTO.class);
            kafkaTemplate.send(deliveryTopic, clientId, objectMapper.writeValueAsString(deliveryDTO));
            LOGGER.info("deliveryDTO = '{}'", deliveryDTO);
            LOGGER.info("delivery = '{}'", delivery);
        }
    }

    /** Idempotence.
     *  Unite data by client id that was received from services:
     *  Customer
     *  Order
     *  and save them to temporary table:
     *  "TEMP"
     *  when the row in table will be full, then save them to model:
     *  Delivery
     *  and remove row, in temporary table
     *
     * @param clientId that define table's row
     * @param consumer universe receiver method
     * @return delivery model
     */
    private Delivery uniteDataReceiver(String clientId, Consumer<Temp> consumer) {
        Optional<Temp> temp = tempRepository.findById(Long.valueOf(clientId));
        Delivery delivery = null;
        if (temp.isEmpty()) {
            temp = Optional.of(new Temp());
            temp.get().setClientId(Long.valueOf(clientId));
            consumer.accept(temp.get());
        } else {
            consumer.accept(temp.get());
        }

        if (temp.get().isReady()) {
            delivery = templateToDelivery(temp.get());
            delivery.setStatus(DeliveryStatus.PENDING);
            tempRepository.delete(temp.get());
        } else {
            tempRepository.save(temp.get());
        }
        LOGGER.info("temp = '{}'", temp.get());
        return delivery;
    }


    private Delivery templateToDelivery(Temp temp) {
        Delivery delivery = new Delivery();
        delivery.setClientId(temp.getClientId());
        delivery.setCustomer(temp.getCustomer());
        delivery.setOrder(temp.getOrder());
        return delivery;
    }

}
