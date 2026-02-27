package com.arka.cart;

import com.arka.enums.ShoppingCartStatus;
import com.arka.model.cart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCartEntity, Long> {

    List<ShoppingCartEntity> findAllByStatus(ShoppingCartStatus status);

    @Query("""
            SELECT sc
            FROM ShoppingCartEntity sc
            WHERE sc.userId = :userId
            AND sc.status = 'ACTIVE'
            """)
    Optional<ShoppingCartEntity> getActiveCartByUserId(@Param("userId") Long userId);
}
