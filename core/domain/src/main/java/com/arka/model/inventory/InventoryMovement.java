package com.arka.model.inventory;

import com.arka.model.Employee;
import com.arka.model.Warehouse;
import com.arka.model.enums.InventoryMovementType;
import com.arka.model.product.Product;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class InventoryMovement {

    private Long id;
    private InventoryMovementType type;
    private int quantity;
    private int previousStock;
    private int newStock;
    private String notes;
    private String registeredAt;
    private Product product;
    private Employee employee;
    private Warehouse warehouse;

}
