package com.arka.model.inventory;

import com.arka.enums.InventoryMovementType;
import com.arka.exceptions.InsufficientStockException;
import com.arka.model.Employee;
import com.arka.model.Warehouse;
import com.arka.model.product.Product;
import com.arka.util.NullValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class WarehouseInventory {

    private Long id;
    private int stock;
    private Warehouse warehouse;
    private Product product;
    private Deque<InventoryMovement> inventoryMovements;

    public static WarehouseInventory create(Warehouse warehouse,
                                            Product product){

        return WarehouseInventory.builder()
                .stock(0)
                .warehouse(warehouse)
                .product(product)
                .inventoryMovements(new ArrayDeque<>())
                .build();
    }

    public void addStock(int quantity, Employee responsible){

        NullValidator.validate(responsible, "responsible");

        validateGreaterThanZero(quantity);

        int newStock = stock + quantity;

        recordMovement(
                InventoryMovementType.IN,
                quantity,
                newStock,
                responsible);

        this.stock = newStock;

    }

    public void removeStock(int quantity, Employee responsible){

        NullValidator.validate(responsible, "responsible");

        validateGreaterThanZero(quantity);

        if(quantity > stock)
            throw new InsufficientStockException(String.format(
                    "The stock to remove [%d] is greater than the actual stock [%d]",
                    quantity, this.stock));

        int newStock = this.stock - quantity;

        recordMovement(
                InventoryMovementType.OUT,
                quantity,
                newStock,
                responsible);

        this.stock = newStock;
    }

    private void recordMovement(InventoryMovementType type,
                                int quantity,
                                int newStock,
                                Employee responsible){

        InventoryMovement newMovement = InventoryMovement.create(
                type,
                quantity,
                this.stock,
                newStock,
                this.product,
                responsible);

        this.inventoryMovements.push(newMovement);
    }

    private void validateGreaterThanZero(int quantity){
        if(quantity <= 0)
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0");
    }
}
