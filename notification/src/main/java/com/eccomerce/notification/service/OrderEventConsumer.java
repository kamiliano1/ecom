package com.eccomerce.notification.service;

//import com.eccomerce.notification.payload.OrderCreatedEventDto;
//import com.eccomerce.notification.payload.OrderStatus;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.eccomerce.notification.payload.OrderCreatedEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
@Slf4j
public class OrderEventConsumer {

    //    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEventDto orderEvent) {
//        System.out.println("Received order event: " + orderEvent);
//        long orderId = orderEvent.getOrderId();
//        OrderStatus orderStatus = orderEvent.getStatus();
//
//        System.out.println("Order ID: " + orderId);
//        System.out.println("Order Status: " + orderStatus);
//
//        // Update Database
//        // Send Notification
//        // Send Emails
//        // Generate Invoice
//        // Send Seller Notification
//    }
    @Bean
    public Consumer<OrderCreatedEventDto> orderCreated() {
        return event -> {
            log.info("Received Order created event for order: {}", event.getOrderId());
            log.info("Received Order created event for userId: {}", event.getUserId());
        };
    }
}
