package com.app.ecom.Repository;

import com.app.ecom.Entity.CartItem;
import com.app.ecom.Entity.Product;
import com.app.ecom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    void deleteByUser(User user);

    List<CartItem> findByUser(User user);
}
