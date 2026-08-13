package com.arka.inventory.warehouse;

import com.arka.employee.EmployeeEntity;
import com.arka.employee.EmployeeEntityMapper;
import com.arka.employee.EmployeeEntityMapperImpl;
import com.arka.entities.Employee;
import com.arka.entities.inventory.WarehouseInventory;
import com.arka.factory.EmployeeTestDataFactory;
import com.arka.factory.ProductTestDataFactory;
import com.arka.factory.WarehouseTestDataFactory;
import com.arka.inventory.movements.InventoryMovementEntityMapperImpl;
import com.arka.product.ProductEntity;
import com.arka.product.ProductEntityMapper;
import com.arka.product.ProductEntityMapperImpl;
import com.arka.warehouse.WarehouseEntity;
import com.arka.warehouse.WarehouseEntityMapper;
import com.arka.warehouse.WarehouseEntityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

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
    private TestEntityManager entityManager;

    private ProductTestDataFactory productTestDataFactory;
    private WarehouseTestDataFactory warehouseTestDataFactory;
    private EmployeeTestDataFactory employeeTestDataFactory;

    private ProductEntity persistedProduct;
    private WarehouseEntity persistedWarehouse;

    @BeforeEach
    void setUp() {
        productTestDataFactory = new ProductTestDataFactory(entityManager);
        warehouseTestDataFactory = new WarehouseTestDataFactory(entityManager);
        employeeTestDataFactory = new EmployeeTestDataFactory(entityManager);

        persistedProduct = productTestDataFactory.createProduct();
        persistedWarehouse = warehouseTestDataFactory.createWarehouse();
    }

    @Test
    void shouldSaveWarehouseInventory() {
        // Given
        WarehouseInventory warehouseInventory =
                WarehouseInventory.create(
                        warehouseEntityMapper.toDomain(persistedWarehouse),
                        productEntityMapper.toDomain(persistedProduct),
                        50);

        // When
        WarehouseInventory savedInventory =
                warehouseInventoryServiceAdapter.save(warehouseInventory);

        // Then
        assertNotNull(savedInventory.getId());

        Optional<WarehouseInventoryEntity> foundInventory =
                warehouseInventoryRepository.findById(savedInventory.getId());

        assertTrue(foundInventory.isPresent());
        assertEquals(savedInventory.getStock(), foundInventory.get().getStock());
        assertEquals(savedInventory.getProduct().getName(),
                foundInventory.get().getProduct().getName());
    }

    @Test
    void shouldMaintainInventoryMovementBidirectionalRelationShipWhenAddStock() {
        // Given
        EmployeeEntity employeeEntity = employeeTestDataFactory.createEmployee();
        Employee employee = employeeEntityMapper.toDomain(employeeEntity);

        WarehouseInventory warehouseInventory =
                WarehouseInventory.create(
                        warehouseEntityMapper.toDomain(persistedWarehouse),
                        productEntityMapper.toDomain(persistedProduct),
                        50);

        // Add stock which records a new movement
        warehouseInventory.addStock(60, employee);

        // When
        WarehouseInventory savedInventory =
                warehouseInventoryServiceAdapter.save(warehouseInventory);

        // Then
        assertFalse(savedInventory.getInventoryMovements().isEmpty());
        assertNotNull(savedInventory.getInventoryMovements().getFirst());

        WarehouseInventoryEntity movement = warehouseInventoryRepository
                .findById(savedInventory.getId()).orElseThrow();

        assertFalse(movement.getInventoryMovements().isEmpty());

        movement.getInventoryMovements().forEach(m ->
                assertNotNull(m.getWarehouseInventory(),
                        "Each movement should reference back to its warehouse inventory"));
    }}
