package ru.ik87.microservices.demo_shop.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ik87.microservices.demo_shop.payment.security.service.JwtService;
import ru.ik87.microservices.demo_shop.payment.security.service.JwtServiceImp;

@SpringBootApplication
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

}
