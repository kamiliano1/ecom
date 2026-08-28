package com.app.ecom.Service;

import com.app.ecom.Entity.CartItem;
import com.app.ecom.Entity.Product;
import com.app.ecom.Entity.User;
import com.app.ecom.Repository.CartItemRepository;
import com.app.ecom.Repository.ProductRepository;
import com.app.ecom.Repository.UserRepository;
import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty()) {
            return false;
        }
        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean removeFromCart(String userId, Long productId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        Optional<Product> productOpt = productRepository.findById(productId);
        if (userOpt.isPresent() && productOpt.isPresent()) {
            User user = userOpt.get();
            Product product = productOpt.get();
            CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
            if (existingCartItem == null) {
                return false;
            }
            userOpt.flatMap(us ->
                    // flatMap używamy, ponieważ lambda zwraca Optional<Boolean>
                    // (productOpt.map(...) zwraca Optional).
                    // flatMap zapobiega powstaniu Optional<Optional<Boolean>>.
                    productOpt.map(prod -> {
                        // map używamy, ponieważ lambda zwraca zwykłą wartość (true).
                        // map automatycznie opakuje true w Optional<Boolean>.
                        cartItemRepository.deleteByUserAndProduct(us, prod);
                        return true;
                    })
            );
            return true;
        }

        return false;
    }

    public List<CartItemResponse> getUserCart(String userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());

    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setPrice(cartItem.getPrice());
        response.setQuantity(cartItem.getQuantity());
        ProductResponse responses = new ProductResponse();
        responses.setId(cartItem.getProduct().getId());
        responses.setName(cartItem.getProduct().getName());
        responses.setDescription(cartItem.getProduct().getDescription());
        responses.setPrice(cartItem.getProduct().getPrice());
        responses.setStockQuantity(cartItem.getProduct().getStockQuantity());
        responses.setCategory(cartItem.getProduct().getCategory());
        responses.setImageUrl(cartItem.getProduct().getImageUrl());
        responses.setActive(cartItem.getProduct().getActive());
        response.setProduct(responses);
        return response;
    }
}

