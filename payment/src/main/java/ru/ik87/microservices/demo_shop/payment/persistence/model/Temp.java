package ru.ik87.microservices.demo_shop.payment.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Temp")
public class Temp {
    @Id
    private Long clientId;
    private Order order;
    private Customer customer;
    private Delivery delivery;

    public Temp() {
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

    public boolean isReady() {
        return order != null && customer != null && delivery != null;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "clientId=" + clientId +
                ", order=" + order +
                ", customer=" + customer +
                ", delivery=" + delivery +
                '}';
    }
}
