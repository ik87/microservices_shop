package ru.ik87.microservices.demo_shop.delivery.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Temp")
public class Temp {
    @Id
    private Long clientId;
    private Customer customer;
    private Order order;

    public Temp() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isReady() {
        return customer != null && order != null;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "clientId=" + clientId +
                ", customer=" + customer +
                ", order=" + order +
                '}';
    }
}
