package com.eccomerce.notification.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(Map<String, Object> orderEvent) {
        System.out.println("Received order event: " + orderEvent);
        long orderId = Long.parseLong(orderEvent.get("orderId").toString());
        String orderStatus = orderEvent.get("status").toString();

        System.out.println("Order ID: " + orderId);
        System.out.println("Order Status: " + orderStatus);

        // Update Database
        // Send Notification
        // Send Emails
        // Generate Invoice
        // Send Seller Notification
    }
}
