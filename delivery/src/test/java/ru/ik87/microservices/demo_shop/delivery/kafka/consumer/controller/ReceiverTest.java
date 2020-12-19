package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ReceiverTest {
    @Test
    public void whenJsonToDtosThenOk() throws JsonProcessingException {


        String orderJson = "{\"orderId\":123412,\"price\":6.1234,\"status\":\"CONFIRMED\"}";
//
//        Order order = new ObjectMapper().readValue(orderJson, OrderDTO.class);
//        assertNotNull(orderDTO);
    }
}