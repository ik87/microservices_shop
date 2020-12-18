package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.model;

public class Order {
    private Long clientId;
    private Long orderId;

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
