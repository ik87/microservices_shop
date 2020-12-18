package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.dto;

public class DeliveryDTO {
    private Long orderId;
    private Double price;
    private String status;

    public DeliveryDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
