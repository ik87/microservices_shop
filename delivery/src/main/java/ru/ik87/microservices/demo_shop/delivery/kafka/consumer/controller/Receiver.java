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
import ru.ik87.microservices.demo_shop.delivery.kafka.dto.CustomerDTO;
import ru.ik87.microservices.demo_shop.delivery.kafka.dto.DeliveryDTO;
import ru.ik87.microservices.demo_shop.delivery.kafka.dto.OrderDTO;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery.Customer;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery.Delivery;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery.DeliveryStatus;
import ru.ik87.microservices.demo_shop.delivery.persistence.model.temporary.Temp;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.DeliveryRepository;
import ru.ik87.microservices.demo_shop.delivery.persistence.repositroy.TempRepository;
import ru.ik87.microservices.demo_shop.delivery.service.CalculateDeliveryPrice;

import java.util.Optional;
import java.util.function.Consumer;

@Controller
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final DeliveryRepository repository;

    private final TempRepository tempRepository;

    @Value("${kafka.producer.topic.delivery}")
    String deliveryTopic;

    @Autowired
    private CalculateDeliveryPrice calculateDeliveryPrice;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public Receiver(DeliveryRepository repository, TempRepository tempRepository) {
        this.tempRepository = tempRepository;
        this.repository = repository;
    }

    @KafkaListener(topics = "${kakfa.consumer.topic.order}")
    public void receiveOrder(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String orderJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("client_id ='{}' order = '{}'", clientId, orderJson);
        //find clientId with state 'NEW' and orderId null or create new customer
        OrderDTO orderDTO = objectMapper.readValue(orderJson, OrderDTO.class);
        //save to temporary db, check if all not null then save to delivery table
        Delivery delivery = saveToTemporaryTable(clientId, temp -> temp.setOrderDTO(orderDTO));

        if (delivery != null) {
            repository.save(delivery);
            sendToTopicDelivery(new ObjectMapper(), clientId, deliveryToDTO(delivery));
            LOGGER.info("delivery = '{}'", delivery);
        }
    }


    @KafkaListener(topics = "${kakfa.consumer.topic.customer}")
    public void receiveCustomer(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String clientId, @Payload String customerJson) throws JsonProcessingException {
        LOGGER.info("client_id ='{}' customer = '{}'", clientId, customerJson);
        //find by clientId with state 'NEW' and orderId null or create new customer
        CustomerDTO customerDTO = new ObjectMapper().readValue(customerJson, CustomerDTO.class);
        //save to temporary db, check if all not null then save to delivery table
        Delivery delivery = saveToTemporaryTable(clientId, temp -> temp.setCustomerDTO(customerDTO));

        if (delivery != null) {
            repository.save(delivery);
            sendToTopicDelivery(new ObjectMapper(), clientId, deliveryToDTO(delivery));
            LOGGER.info("delivery = '{}'", delivery);
        }
    }

    //save to temporary db, check if all not null then save to delivery table
    private Delivery saveToTemporaryTable(String clientId, Consumer<Temp> templateConsumer) {
        Optional<Temp> temp = tempRepository.findById(Long.valueOf(clientId));
        Delivery delivery = null;
        if (temp.isEmpty()) {
            temp = Optional.of(new Temp());
            temp.get().setClientId(Long.valueOf(clientId));
            templateConsumer.accept(temp.get());
        } else {
            templateConsumer.accept(temp.get());
        }

        if (temp.get().isReady()) {
            delivery = templateToDelivery(temp.get());
            delivery.setPrice(calculateDeliveryPrice.calc(delivery));
            delivery.setStatus(DeliveryStatus.PENDING);
            tempRepository.delete(temp.get());
        } else {
            tempRepository.save(temp.get());
        }
        LOGGER.info("temp = '{}'", temp.get());
        return delivery;
    }


    @KafkaListener(topics = "${kakfa.consumer.topic.payment}")
    public void receiveSendCommand(Long deliveryId) throws JsonProcessingException {
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

    private Delivery templateToDelivery(Temp temp) {
        Delivery delivery = new Delivery();
        delivery.setClientId(temp.getClientId());
        delivery.setCustomer(customerFromDto(temp.getCustomerDTO()));
        delivery.setOrderId(temp.getOrderDTO().getOrderId());
        return delivery;
    }

    private void sendToTopicDelivery(ObjectMapper objectMapper, String key, DeliveryDTO deliveryDTO) throws JsonProcessingException {
        kafkaTemplate.send(deliveryTopic, key, objectMapper.writeValueAsString(deliveryDTO));
    }
}
