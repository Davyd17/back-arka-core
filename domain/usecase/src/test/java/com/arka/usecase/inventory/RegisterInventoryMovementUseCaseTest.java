package com.arka.usecase.inventory;

import com.arka.inventory.RegisterInventoryMovementUseCase;
import com.arka.inventory.dto.CreateInventoryMovementIn;
import com.arka.enums.InventoryMovementType;
import com.arka.inventory.gateway.WarehouseInventoryGateway;
import com.arka.inventory.mapper.InventoryMovementMapper;
import com.arka.model.Employee;
import com.arka.model.Warehouse;
import com.arka.model.information.Contact;
import com.arka.model.inventory.WarehouseInventory;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import com.arka.party.service.EmployeeService;
import com.arka.inventory.service.WarehouseInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterInventoryMovementUseCaseTest {

    @Mock
    private InventoryMovementMapper movementMapper;

    @Mock
    private WarehouseInventoryGateway inventoryGateway;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private WarehouseInventoryService inventoryService;

    @InjectMocks
    private RegisterInventoryMovementUseCase useCase;

    private Employee employee;

    private Warehouse warehouse;

    @BeforeEach
    void setUp(){

        Contact contact =
                new Contact(1L, "Jhon", "Doe", "warehouse manager",
                        "employee@arka.com", new ArrayList<>(), new ArrayList<>(), true, 2L);

        employee = new Employee(1L, 1234, contact);

        warehouse = new Warehouse(1L, true, null);
    }

    private Product buildProduct(long id, BigDecimal basePrice) {
        return new Product(
                id,
                "PT-00" + id,
                "test product-" + id,
                null,
                basePrice,
                new HashMap<>(),
                ProductCategory.create("Test category"),
                true
        );
    }

    private WarehouseInventory buildWarehouseInventory(Long id, int stock, Product product){
        return new WarehouseInventory(
                id,
                stock,
                warehouse,
                product,
                new ArrayDeque<>());
    }

    private CreateInventoryMovementIn buildInput(InventoryMovementType type, int quantity, Long productId){
        return new CreateInventoryMovementIn(
                type, quantity, null,
                productId, 1L, 1L);
    }

    // --- input validator --

    @Test
    void shouldThrowWhenInputIsNull (){
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null));
    }

    @Test
    void shouldAddStockWhenMovementTypeIN() {

        Product product = buildProduct(1L, BigDecimal.valueOf(20.00));

        WarehouseInventory inventory = buildWarehouseInventory(1L, 20, product);

        CreateInventoryMovementIn input =
                buildInput(InventoryMovementType.IN, 10, 1L);

        when(employeeService.findById(1L)).thenReturn(employee);
        when(inventoryService.findByProductAndWarehouse(1L, 1L))
                .thenReturn(inventory);

        when(inventoryGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryGateway).save(argThat(inv ->
                inv.getStock() == 30));
    }

    @Test
    void shouldRemoveStockWhenMovementTypeOUT() {

        Product product = buildProduct(1L, BigDecimal.valueOf(20.00));

        WarehouseInventory inventory = buildWarehouseInventory(1L, 20, product);

        CreateInventoryMovementIn input =
                buildInput(InventoryMovementType.OUT, 10, 1L);

        when(employeeService.findById(1L)).thenReturn(employee);
        when(inventoryService.findByProductAndWarehouse(1L, 1L))
                .thenReturn(inventory);

        when(inventoryGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryGateway).save(argThat(inv ->
                inv.getStock() == 10));
    }

    @Test
    void shouldRegisterMovementAfterStockOperation(){

        Product product = buildProduct(1L, BigDecimal.valueOf(20.00));

        WarehouseInventory inventory = buildWarehouseInventory(1L, 20, product);

        CreateInventoryMovementIn input =
                buildInput(InventoryMovementType.IN, 10, 1L);

        when(employeeService.findById(1L)).thenReturn(employee);
        when(inventoryService.findByProductAndWarehouse(1L, 1L))
                .thenReturn(inventory);

        when(inventoryGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryGateway).save(argThat(inv ->
                inv.getInventoryMovements().size() == 1 &&
                inv.getInventoryMovements().getFirst().getPreviousStock() == 20));
    }

    @Test
    void shouldCallGatewaySave(){

        Product product = buildProduct(1L, BigDecimal.valueOf(20.00));

        WarehouseInventory inventory = buildWarehouseInventory(1L, 20, product);

        CreateInventoryMovementIn input =
                buildInput(InventoryMovementType.OUT, 10, 1L);

        when(employeeService.findById(1L)).thenReturn(employee);
        when(inventoryService.findByProductAndWarehouse(1L, 1L))
                .thenReturn(inventory);

        when(inventoryGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryGateway).save(any());
    }

}