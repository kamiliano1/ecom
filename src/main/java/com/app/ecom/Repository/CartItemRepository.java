package com.app.ecom.Repository;

import com.app.ecom.Entity.CartItem;
import com.app.ecom.Entity.Product;
import com.app.ecom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

}
