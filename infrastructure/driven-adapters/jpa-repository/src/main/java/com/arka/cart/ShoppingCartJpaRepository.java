package com.arka.cart;

import com.arka.enums.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCartEntity, Long> {

    List<ShoppingCartEntity> findAllByStatus(ShoppingCartStatus status);

    Optional<ShoppingCartEntity> findFirstByUserIdOrderByCreatedAtDesc(Long userId);
}
