package com.arka.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCartEntity, Long> {
}
