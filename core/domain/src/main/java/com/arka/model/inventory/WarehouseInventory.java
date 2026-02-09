package com.arka.model.inventory;

import com.arka.model.Warehouse;
import com.arka.model.product.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder(toBuilder = true)
@Getter
public class WarehouseInventory {

    private Long id;
    private int stock;
    private Instant createdAt;
    private Instant updatedAt;
    private Warehouse warehouse;
    private Product product;
}
