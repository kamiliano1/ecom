package com.app.ecom.Controller;

import com.app.ecom.Entity.CartItem;
import com.app.ecom.Service.CartService;
import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.dto.ProductResponse;
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
            @RequestHeader("X-User-ID") String userId
    ) {
        return new ResponseEntity<>(cartService.getUserCart(userId), HttpStatus.OK);

    }

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
