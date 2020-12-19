package ru.ik87.microservices.demo_shop.payment.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name="PAYMENTS")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    @JsonIgnore
    private Long clientId;

    @AttributeOverride(
            name = "price",
            column = @Column( name = "order_price" )
    )
    private Order order;
    private Customer customer;
    @AttributeOverride(
            name = "price",
            column = @Column( name = "delicery_price" )
    )
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
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

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", clientId=" + clientId +
                ", order=" + order +
                ", customer=" + customer +
                ", delivery=" + delivery +
                ", status=" + status +
                ", timeChangeStatus=" + timeChangeStatus +
                '}';
    }
}
