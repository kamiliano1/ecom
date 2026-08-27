package com.app.ecom.Controller;

import com.app.ecom.Service.CartService;
import com.app.ecom.dto.CartItemRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {

    private CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        return cartService.addToCart(userId, request)
                ? new ResponseEntity<String>(HttpStatus.CREATED)
                : new ResponseEntity<String>("Product Out of Stock or User not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {
        return cartService.removeFromCart(userId, productId)
                ? new ResponseEntity<String>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<String>("Product Out of Stock or User not found", HttpStatus.NOT_FOUND);
    }
}
