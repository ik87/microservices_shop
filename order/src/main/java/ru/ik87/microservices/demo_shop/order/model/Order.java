package ru.ik87.microservices.demo_shop.order.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "ORDERS")
public class Order {
    @Id
    @NotNull
    private Long orderId;
    @NotNull
    @JsonIgnore
    private Long clientId;
    @NotNull
    private Double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @JsonIgnore
    private Long timeChangeStatus;

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        this.timeChangeStatus = new Date().getTime();
    }

    public Long getTimeChangeStatus() {
        return timeChangeStatus;
    }

}
