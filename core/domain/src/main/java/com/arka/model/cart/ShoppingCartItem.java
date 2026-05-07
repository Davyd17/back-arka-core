package com.arka.model.cart;

import com.arka.model.product.Product;
import com.arka.util.QuantityValidator;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ShoppingCartItem {

    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    private final Product product;

    public static ShoppingCartItem create(
            int quantity,
            Product product){

        QuantityValidator.validate(quantity);

        return ShoppingCartItem.builder()
                .quantity(quantity)
                .unitPrice(product.getBasePrice())
                .subTotal(product.getBasePrice()
                                .multiply(BigDecimal.valueOf(quantity)))
                .product(product)
                .build();
    }

    public void updateQuantity(int quantity) {

        QuantityValidator.validate(quantity);

        this.quantity = quantity;
        updateSubTotal();
    }

    private void updateSubTotal() {
        this.subTotal =
                this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

}
