package com.ecommerce.order.Repository;

import com.ecommerce.order.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    CartItem findByUserIdAndProductId(String userId, Long productId);

    void deleteByUserIdAndProductId(String userId, Long productId);

    void deleteByUserId(String userId);

    List<CartItem> findByUserId(String userId);
}
