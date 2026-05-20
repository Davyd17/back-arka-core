package com.arka.model.order;

import com.arka.model.product.Product;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Product product;
    private int quantity;
    private BigDecimal unitPriceSnapshot;
    private BigDecimal totalPrice;

    public static OrderItem create(Product product,
                                   int quantity) {

        validateGraterThanZero(quantity, product);

        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPriceSnapshot(product.getBasePrice())
                .totalPrice(product.getBasePrice()
                        .multiply(BigDecimal.valueOf(quantity)))
                .build();
    }

    public void updateQuantity(int quantity) {

        validateGraterThanZero(quantity, this.product);

        this.quantity = quantity;
        this.updateTotalPrice();
    }

    private static void validateGraterThanZero(int quantity, Product product) {

        if (quantity <= 0)
            throw new
                    IllegalArgumentException(
                    String.format("Quantity must be greater than zero for product [%s]",
                            product.getSku()));
    }

    private void updateTotalPrice() {

        this.totalPrice = this.unitPriceSnapshot
                .multiply(BigDecimal.valueOf(this.quantity));
    }
}
