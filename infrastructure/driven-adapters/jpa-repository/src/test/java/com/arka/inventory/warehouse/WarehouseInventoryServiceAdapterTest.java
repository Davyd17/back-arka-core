package com.arka.inventory.warehouse;

import com.arka.employee.EmployeeEntityMapper;
import com.arka.employee.EmployeeEntityMapperImpl;
import com.arka.employee.EmployeeRepository;
import com.arka.entities.Employee;
import com.arka.entities.inventory.InventoryMovement;
import com.arka.entities.inventory.WarehouseInventory;
import com.arka.inventory.movements.InventoryMovementEntity;
import com.arka.inventory.movements.InventoryMovementEntityMapperImpl;
import com.arka.inventory.movements.InventoryMovementRepository;
import com.arka.product.ProductEntity;
import com.arka.product.ProductEntityMapper;
import com.arka.product.ProductEntityMapperImpl;
import com.arka.product.ProductRepository;
import com.arka.product.category.ProductCategoryRepository;
import com.arka.warehouse.WarehouseEntity;
import com.arka.warehouse.WarehouseEntityMapper;
import com.arka.warehouse.WarehouseEntityMapperImpl;
import com.arka.warehouse.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
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
        EmployeeEntityMapperImpl.class,
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

    @Autowired
    private EmployeeEntityMapper employeeEntityMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    private ProductEntity persistedProduct;

    @BeforeEach
    void setUp(){

        persistedProduct = productRepository.save(ProductEntity.builder()
                .name("test product")
                .category(categoryRepository.findById(1L).orElseThrow())
                .attributes(new HashMap<>())
                .active(true)
                .description("Test Description")
                .basePrice(BigDecimal.TEN)
                .sku("Test-001")
                .build());
    }


    @Test
    void shouldSaveWarehouseInventory() {

        //Given
        WarehouseEntity warehouse =
                warehouseRepository.findById(1L).orElseThrow();

        WarehouseInventory warehouseInventory =
                WarehouseInventory.create(
                        warehouseEntityMapper.toDomain(warehouse),
                        productEntityMapper.toDomain(persistedProduct),
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

    @Test
    void shouldMaintainInventoryMovementBidirectionalRelationShipWhenAddStock(){

        //Given
        WarehouseEntity warehouse =
                warehouseRepository.findById(1L).orElseThrow();

        Employee employee = employeeEntityMapper.toDomain(
                employeeRepository.findById(1L).orElseThrow());

        WarehouseInventory warehouseInventory =
                WarehouseInventory.create(
                        warehouseEntityMapper.toDomain(warehouse),
                        productEntityMapper.toDomain(persistedProduct),
                        50);

        //Add stock which record a new movement
        warehouseInventory.addStock(60, employee);

        //When
        WarehouseInventory savedInventory =
                warehouseInventoryServiceAdapter.save(warehouseInventory);

        //Then
        assertFalse(savedInventory.getInventoryMovements().isEmpty());
        assertNotNull(savedInventory.getInventoryMovements().getFirst());

        WarehouseInventoryEntity movement = warehouseInventoryRepository
                .findById(savedInventory.getId()).orElseThrow();

        assertFalse(movement.getInventoryMovements().isEmpty());

        movement.getInventoryMovements().forEach(m ->
                assertNotNull(m.getWarehouseInventory(),
                        "Each movement should reference back to its warehouse inventory"));



    }
}