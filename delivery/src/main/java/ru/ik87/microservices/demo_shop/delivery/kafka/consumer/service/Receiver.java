package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.DeliveryRepository;

@Controller
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    private final DeliveryRepository repository;

    public Receiver(DeliveryRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = {"${kakfa.consumer.topic.json.order}"})
    public void receiveOrder(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String orderJson) {
        LOGGER.info("client_id ='{}' order = '{}'", clientId, orderJson);
    }
    @KafkaListener(topics = {"${kakfa.consumer.topic.json.customer}"})
    public void receiveCustomer(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String customerJson) {
        LOGGER.info("client_id ='{}' customer = '{}'", clientId, customerJson);
    }

    @KafkaListener(topics = {"${kakfa.consumer.topic.json.send}"})
    public void receiveSendCommand(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String orderId) {
        LOGGER.info("client_id ='{}' customer = '{}'", clientId, orderId);
    }
}
