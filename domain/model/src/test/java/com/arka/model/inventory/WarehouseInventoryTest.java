package com.arka.model.inventory;

import com.arka.entities.Employee;
import com.arka.entities.Warehouse;
import com.arka.entities.inventory.WarehouseInventory;
import com.arka.entities.product.Product;
import com.arka.enums.InventoryMovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseInventoryTest {

    private WarehouseInventory inventory;
    private Warehouse warehouse;
    private Product product;

    @BeforeEach
    void setUp() {

    }

    private Product buildProduct(Long id, BigDecimal price, boolean active) {
        return new Product(id, "SKU-00" + id, "Product " + id, null,
                price, new HashMap<>(), null, active);
    }

    private Warehouse buildWarehouse(Long id, boolean active){
        return new Warehouse(id, active, null);
    }

    private Employee buildEmployee(Long id){
        return new Employee(id, Math.toIntExact(10 + id), null);
    }

    // -- create --

    @Test
    void shouldCreateInventoryWithInitialStock(){
        warehouse = buildWarehouse(1L, true);
        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        inventory = WarehouseInventory.create(warehouse, product, 20);

        assertEquals(20, inventory.getStock());
    }

    @Test
    void shouldThrowWhenInitialStockIsZeroOrNegative(){
        warehouse = buildWarehouse(1L, true);
        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        assertThrows(IllegalArgumentException.class, () ->
                WarehouseInventory.create(warehouse, product, 0));

        assertThrows(IllegalArgumentException.class, () ->
                WarehouseInventory.create(warehouse, product, -4));
    }

    //-- addStock --
    @Test
    void shouldAddStockToExistenceProduct(){
        warehouse = buildWarehouse(1L, true);
        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);
        inventory = WarehouseInventory.create(warehouse, product, 20);

        inventory.addStock(20, buildEmployee(2L));

        assertEquals(40, inventory.getStock());
    }

    @Test
    void shouldAddInventoryMovementWhenAddStock(){
        warehouse = buildWarehouse(1L, true);
        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        inventory = WarehouseInventory.create(warehouse, product, 20);

        inventory.addStock(10, buildEmployee(3L));

        assertEquals(1, inventory.getInventoryMovements().size());
        assertThat(inventory.getInventoryMovements().getFirst().getType())
                .isEqualByComparingTo(InventoryMovementType.IN);
    }


    @Test
    void shouldRemoveStock(){
        warehouse = buildWarehouse(1L, true);
        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        inventory = WarehouseInventory.create(warehouse, product, 20);

        inventory.removeStock(10, buildEmployee(1L));

        assertEquals(10, inventory.getStock());
    }

    @Test
    void shouldAddInventoryMovementWhenRemoveAStock(){
        warehouse = buildWarehouse(1L, true);
        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        inventory = WarehouseInventory.create(warehouse, product, 20);

        inventory.removeStock(10, buildEmployee(3L));

        assertEquals(1, inventory.getInventoryMovements().size());
        assertThat(inventory.getInventoryMovements().getFirst().getType())
                .isEqualByComparingTo(InventoryMovementType.OUT);
    }




}