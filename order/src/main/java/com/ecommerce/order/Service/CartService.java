package com.ecommerce.order.Service;

import com.ecommerce.order.Entity.CartItem;
import com.ecommerce.order.Repository.CartItemRepository;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
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

    public boolean addToCart(Long userId, CartItemRequest request) {
//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//        if (productOpt.isEmpty()) {
//            return false;
//        }
//        Product product = productOpt.get();
//        if (product.getStockQuantity() < request.getQuantity()) {
//            return false;
//        }
//
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty()) {
//            return false;
//        }
//        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
//            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
//            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean removeFromCart(Long userId, Long productId) {
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        Optional<Product> productOpt = productRepository.findById(productId);
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {

            cartItemRepository.delete(cartItem);
            return true;
//            if (existingCartItem == null) {
//                return false;
//            }
//            userOpt.flatMap(us ->
//                    // flatMap używamy, ponieważ lambda zwraca Optional<Boolean>
//                    // (productOpt.map(...) zwraca Optional).
//                    // flatMap zapobiega powstaniu Optional<Optional<Boolean>>.
//                    productOpt.map(prod -> {
//                        // map używamy, ponieważ lambda zwraca zwykłą wartość (true).
//                        // map automatycznie opakuje true w Optional<Boolean>.
//                        cartItemRepository.deleteByUserAndProduct(us, prod);
//                        return true;
//                    })
//            );
        }
        return false;
    }

    public List<CartItemResponse> getUserCart(Long userId) {
//        return userRepository.findById(Long.valueOf(userId))
//                .map(cartItemRepository::findByUser)
//                .orElseGet(List::of)
//                .stream().map(this::mapToCartItemResponse)
//                .collect(Collectors.toList());
        return cartItemRepository.findByUserId(userId)
                .stream().map(this::mapToCartItemResponse)
                .collect(Collectors.toList());
    }

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setTotalPrice(cartItem.getPrice());
        response.setQuantity(cartItem.getQuantity());
//        ProductResponse productResponse = productService.mapToProductResponse(cartItem.getProduct());
//        response.setProduct(productResponse);
        return response;
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
