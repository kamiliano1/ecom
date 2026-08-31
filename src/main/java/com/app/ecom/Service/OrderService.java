package com.app.ecom.Service;

import com.app.ecom.Entity.*;
import com.app.ecom.Repository.CartItemRepository;
import com.app.ecom.Repository.OrderRepository;
import com.app.ecom.Repository.UserRepository;
import com.app.ecom.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartRepository;
    private final CartService cartService;
    private final UserRepository userRepository;

    public OrderResponse createOrder(String userId) {
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return null;
        }
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);
        return mapToOrderResponse(savedOrder);
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        OrderResponse response = new OrderResponse();
        response.setStatus(savedOrder.getStatus());
        response.setTotalAmount(savedOrder.getTotalAmount());
        response.setItems();
    }
}
