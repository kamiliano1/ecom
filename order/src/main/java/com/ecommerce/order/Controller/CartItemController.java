package com.ecommerce.order.Controller;

import com.ecommerce.order.Service.CartService;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {

    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getUserCart(
            @RequestHeader("X-User-ID") Long userId
    ) {
        return new ResponseEntity<>(cartService.getUserCart(userId), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") Long userId,
            @RequestBody CartItemRequest request) {
        return cartService.addToCart(userId, request)
                ? new ResponseEntity<String>(HttpStatus.CREATED)
                : new ResponseEntity<String>("Product Out of Stock or User not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long productId) {
        return cartService.removeFromCart(userId, productId)
                ? new ResponseEntity<String>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<String>("Product Out of Stock or User not found", HttpStatus.NOT_FOUND);
    }
}
