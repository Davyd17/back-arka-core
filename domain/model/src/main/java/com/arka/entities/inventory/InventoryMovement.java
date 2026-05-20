package com.arka.entities.inventory;

import com.arka.entities.Employee;
import com.arka.entities.product.Product;
import com.arka.enums.InventoryMovementType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class InventoryMovement {

    private Long id;
    private InventoryMovementType type;
    private int quantity;
    private int previousStock;
    private int newStock;
    private String notes;
    private Instant registeredAt;
    private Product product;
    private Employee employee;

    public static InventoryMovement create(
            InventoryMovementType type,
            int quantity,
            int previousStock,
            int newStock,
            Product product,
            Employee employee
    ) {

        return InventoryMovement
                .builder()
                .type(type)
                .quantity(quantity)
                .previousStock(previousStock)
                .newStock(newStock)
                .product(product)
                .employee(employee)
                .build();
    }

    public void updateNotes(String notes){
        this.notes = notes;
    }

}
