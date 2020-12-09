package ru.ik87.microservices.demo_shop.orders.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "ORDERS")
public class Order {
    @Id
    @NotNull
    private Long orderId;

    @NotNull
    private Long clientId;
    @NotNull
    private Double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long timeChangeStatus;

    public Order() {
    }

    public Order(@NotNull Long orderId, @NotNull Long clientId, @NotNull Double price) {
        this.price = price;
        this.status = OrderStatus.NEW;
        this.timeChangeStatus = new Date().getTime();
        this.clientId = clientId;
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", price=" + price +
                ", status=" + status +
                ", timeChangeStatus=" + timeChangeStatus +
                "}";
    }
}
