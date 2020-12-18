package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.service;

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
import ru.ik87.microservices.demo_shop.delivery.kafka.consumer.dto.CustomerDTO;
import ru.ik87.microservices.demo_shop.delivery.kafka.consumer.dto.DeliveryDTO;
import ru.ik87.microservices.demo_shop.delivery.kafka.consumer.dto.OrderDTO;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Customer;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.DeliveryStatus;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.DeliveryRepository;
import ru.ik87.microservices.demo_shop.delivery.service.CalculateDeliveryPrice;

@Controller
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final DeliveryRepository repository;

    @Value("${kafka.producer.topic.delivery}")
    String deliveryTopic;

    @Autowired
    private CalculateDeliveryPrice calculateDeliveryPrice;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public Receiver(DeliveryRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${kakfa.consumer.topic.order}")
    public void receiveOrder(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String orderJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("client_id ='{}' order = '{}'", clientId, orderJson);
        //find clientId with state 'NEW' and orderId null or create new customer
        OrderDTO orderDTO = objectMapper.readValue(orderJson, OrderDTO.class);
        Delivery delivery = repository.findByClientIdAndStatus(Long.valueOf(clientId), DeliveryStatus.NEW);
        if (delivery != null) {
            delivery.setOrderId(orderDTO.getOrderId());
            delivery.setPrice(calculateDeliveryPrice.calc(delivery));
            delivery.setStatus(DeliveryStatus.PENDING);
            repository.save(delivery);
            sendToTopicDelivery(objectMapper, clientId, deliveryToDTO(delivery));

        } else {
            delivery = new Delivery();
            delivery.setOrderId(orderDTO.getOrderId());
            delivery.setClientId(Long.valueOf(clientId));
            delivery.setStatus(DeliveryStatus.NEW);
            repository.save(delivery);
        }

        LOGGER.info("delivery = '{}'", delivery);
    }



    @KafkaListener(topics = "${kakfa.consumer.topic.customer}")
    public void receiveCustomer(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String customerJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("client_id ='{}' customer = '{}'", clientId, customerJson);
        //find by clientId with state 'NEW' and orderId null or create new customer
        CustomerDTO customerDTO = objectMapper.readValue(customerJson, CustomerDTO.class);
        Delivery delivery = repository.findByClientIdAndStatus(Long.valueOf(clientId), DeliveryStatus.NEW);
        if(delivery != null) {
            Customer customer = customerFromDto(customerDTO);
            delivery.setCustomer(customer);
            delivery.setPrice(calculateDeliveryPrice.calc(delivery));
            delivery.setStatus(DeliveryStatus.PENDING);
            repository.save(delivery);
            sendToTopicDelivery(objectMapper, clientId, deliveryToDTO(delivery));
        } else {
            delivery = new Delivery();
            Customer customer = customerFromDto(customerDTO);
            delivery.setClientId(Long.valueOf(clientId));
            delivery.setCustomer(customer);
            delivery.setStatus(DeliveryStatus.NEW);
            repository.save(delivery);
        }
        LOGGER.info("delivery = '{}'", delivery);
    }



    @KafkaListener(topics = "${kakfa.consumer.topic.payment}")
    public void receiveSendCommand(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload Long orderId) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' orderId = '{}'", clientId, orderId);
        Delivery delivery = repository.findByOrderIdAndClientId(orderId, Long.valueOf(clientId));
        if(delivery != null) {
            if (delivery.getStatus() == DeliveryStatus.PENDING) {
                delivery.setStatus(DeliveryStatus.SEND);
                LOGGER.info("delivery = '{}'", delivery);
            } else if (delivery.getStatus() == DeliveryStatus.SEND) {
                LOGGER.info("delivery have been sent = '{}' ", delivery);
            } else {
                LOGGER.info("delivery is raw = '{}' ", delivery);
            }
            sendToTopicDelivery(new ObjectMapper(), clientId, deliveryToDTO(delivery));
        }
        LOGGER.info("delivery = '{}'", delivery);
    }


    private Customer customerFromDto(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setAddress(customerDTO.getAddress());
        customer.setEmail(customerDTO.getEmail());
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        return customer;
    }

    private DeliveryDTO deliveryToDTO(Delivery delivery) {
        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setOrderId(delivery.getOrderId());
        deliveryDTO.setPrice(delivery.getPrice());
        deliveryDTO.setStatus(delivery.getStatus().name());
        return deliveryDTO;
    }

    private void sendToTopicDelivery(ObjectMapper objectMapper, String key, DeliveryDTO deliveryDTO) throws JsonProcessingException {
        kafkaTemplate.send(deliveryTopic, key, objectMapper.writeValueAsString(deliveryDTO));
    }
}
