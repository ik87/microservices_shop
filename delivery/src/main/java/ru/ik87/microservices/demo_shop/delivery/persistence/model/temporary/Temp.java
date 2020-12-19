package ru.ik87.microservices.demo_shop.delivery.persistence.model.temporary;

import ru.ik87.microservices.demo_shop.delivery.kafka.dto.CustomerDTO;
import ru.ik87.microservices.demo_shop.delivery.kafka.dto.OrderDTO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Temp")
public class Temp {
    @Id
    private Long clientId;
    private CustomerDTO customerDTO;
    private OrderDTO orderDTO;

    public Temp() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public boolean isReady() {
        return customerDTO != null && orderDTO != null;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "clientId=" + clientId +
                ", customerDTO=" + customerDTO +
                ", orderDTO=" + orderDTO +
                '}';
    }
}
