package ru.ik87.microservices.demo_shop.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name="PAYMENTS")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    @NotNull
    @JsonIgnore
    private Long clientId;

    @NotNull
    private Long orderId;

    @NotNull
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @JsonIgnore
    private Long timeChangeStatus;

    public Payment() {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
        this.timeChangeStatus = new Date().getTime();
    }

    public Long getTimeChangeStatus() {
        return timeChangeStatus;
    }

    public void setTimeChangeStatus(Long timeChangeStatus) {
        this.timeChangeStatus = timeChangeStatus;
    }
}
