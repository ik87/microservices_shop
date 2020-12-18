package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ik87.microservices.demo_shop.delivery.kafka.consumer.dto.OrderDTO;

import static org.junit.jupiter.api.Assertions.*;


class ReceiverTest {
    @Test
    public void whenJsonToDtosThenOk() throws JsonProcessingException {
        String orderJson = "{\"orderId\":123412,\"price\":6.1234,\"status\":\"CONFIRMED\"}";
         OrderDTO orderDTO = new ObjectMapper().readValue(orderJson, OrderDTO.class);
       //OrderDTO orderDTO = new Gson().fromJson(orderJson, OrderDTO.class);
        assertNotNull(orderDTO);
    }
}