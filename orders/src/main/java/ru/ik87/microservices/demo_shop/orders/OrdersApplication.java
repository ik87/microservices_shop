package ru.ik87.microservices.demo_shop.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.ik87.microservices.demo_shop.orders.model.Order;
import ru.ik87.microservices.demo_shop.orders.model.OrderStatus;
import ru.ik87.microservices.demo_shop.orders.repostitory.OrderRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OrdersApplication {
    private static final Logger LOG = LoggerFactory.getLogger(OrdersApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
