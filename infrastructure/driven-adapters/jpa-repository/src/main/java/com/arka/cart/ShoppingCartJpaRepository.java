package com.arka.cart;

import com.arka.enums.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCartEntity, Long> {

    List<ShoppingCartEntity> findAllByStatus(ShoppingCartStatus status);
}
