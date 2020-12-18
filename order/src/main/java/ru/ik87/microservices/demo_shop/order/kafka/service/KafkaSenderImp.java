package ru.ik87.microservices.demo_shop.order.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.ik87.microservices.demo_shop.order.persistence.model.Order;

public class KafkaSenderImp implements KafkaSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSenderImp.class);

    private final String jsonTopic;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public KafkaSenderImp(String jsonTopic) {
        this.jsonTopic = jsonTopic;
    }

    @Override
    public void send(Order order) {
        LOGGER.info("sending order='{}'", order.toString());
        kafkaTemplate.send(jsonTopic, order.getClientId().toString(), order);
    }
}
