package ru.ik87.microservices.demo_shop.delivery.persistence.model.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "DELIVERIES")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long deliveryId;
    @JsonIgnore
    private Long clientId;
    private Long orderId;
    private Double price;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @JsonIgnore
    private Long timeChangeStatus;
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Delivery() {
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
        this.timeChangeStatus = new Date().getTime();
    }

    public Long getTimeChangeStatus() {
        return timeChangeStatus;
    }

    public void setTimeChangeStatus(Long timeChangeStatus) {
        this.timeChangeStatus = timeChangeStatus;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "deliveryId=" + deliveryId +
                ", clientId=" + clientId +
                ", orderId=" + orderId +
                ", price=" + price +
                ", status=" + status +
                ", timeChangeStatus=" + timeChangeStatus +
                ", customer=" + customer +
                '}';
    }
}
