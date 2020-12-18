package ru.ik87.microservices.demo_shop.delivery.kafka.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;

public class KafkaSenderImp implements KafkaSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSenderImp.class);

    private final String jsonTopic;

    @Autowired
    private KafkaTemplate<String, Delivery> kafkaTemplate;

    public KafkaSenderImp(String jsonTopic) {
        this.jsonTopic = jsonTopic;
    }

    @Override
    public void send(Delivery delivery) {
        LOGGER.info("sending order='{}'", delivery.toString());
        kafkaTemplate.send(jsonTopic, delivery.getClientId().toString(), delivery);
    }
}
