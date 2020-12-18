package ru.ik87.microservices.demo_shop.customer.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.ik87.microservices.demo_shop.customer.persistence.model.Customer;

public class KafkaSenderImp implements KafkaSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSenderImp.class);

    private final String jsonTopic;

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    public KafkaSenderImp(String jsonTopic) {
        this.jsonTopic = jsonTopic;
    }

    @Override
    public void send(Customer customer) {
        LOGGER.info("sending order='{}'", customer.toString());
        kafkaTemplate.send(jsonTopic, customer.getClientId().toString(), customer);
    }
}
