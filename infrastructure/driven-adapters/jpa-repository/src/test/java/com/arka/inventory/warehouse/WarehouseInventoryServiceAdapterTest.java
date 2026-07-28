package com.arka.inventory.warehouse;

import com.arka.entities.inventory.WarehouseInventory;
import com.arka.inventory.movements.InventoryMovementEntityMapperImpl;
import com.arka.product.ProductEntity;
import com.arka.product.ProductEntityMapper;
import com.arka.product.ProductEntityMapperImpl;
import com.arka.product.ProductRepository;
import com.arka.product.category.ProductCategoryRepository;
import com.arka.warehouse.WarehouseEntity;
import com.arka.warehouse.WarehouseEntityMapper;
import com.arka.warehouse.WarehouseEntityMapperImpl;
import com.arka.warehouse.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({WarehouseInventoryServiceAdapter.class,
        WarehouseInventoryEntityMapperImpl.class,
        InventoryMovementEntityMapperImpl.class,
        ProductEntityMapperImpl.class,
        WarehouseEntityMapperImpl.class})
@ActiveProfiles("test")
class WarehouseInventoryServiceAdapterTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseInventoryRepository warehouseInventoryRepository;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Autowired
    private WarehouseEntityMapper warehouseEntityMapper;

    @Autowired
    private WarehouseInventoryServiceAdapter warehouseInventoryServiceAdapter;


    @Test
    void shouldSaveWarehouseInventoryRelationship() {

        //Given
        WarehouseEntity warehouse =
                warehouseRepository.findById(1L).orElseThrow();

        ProductEntity savedProduct = productRepository.save(ProductEntity.builder()
                .name("test product")
                .category(categoryRepository.findById(1L).orElseThrow())
                .attributes(new HashMap<>())
                .active(true)
                .description("Test Description")
                .basePrice(BigDecimal.TEN)
                .sku("Test-001")
                .build());

        WarehouseInventory warehouseInventory =
                WarehouseInventory.create(
                        warehouseEntityMapper.toDomain(warehouse),
                        productEntityMapper.toDomain(savedProduct),
                        50);

        //When
        WarehouseInventory savedInventory =
                warehouseInventoryServiceAdapter.save(warehouseInventory);

        //Then
        assertNotNull(savedInventory.getId());

        Optional<WarehouseInventoryEntity> foundInventory =
                warehouseInventoryRepository.findById(savedInventory.getId());

        assertTrue(foundInventory.isPresent());
        assertEquals(savedInventory.getStock(), foundInventory.get().getStock());
        assertEquals(savedInventory.getProduct().getName(),
                foundInventory.get().getProduct().getName());
    }
}