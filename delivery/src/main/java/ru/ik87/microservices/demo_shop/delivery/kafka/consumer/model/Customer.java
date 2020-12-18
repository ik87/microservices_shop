package ru.ik87.microservices.demo_shop.delivery.kafka.consumer.model;


public class Customer {
    private String address;

    public Customer() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                '}';
    }
}
