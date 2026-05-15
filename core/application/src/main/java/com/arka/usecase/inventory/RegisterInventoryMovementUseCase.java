package com.arka.usecase.inventory;

import com.arka.dto.in.CreateInventoryMovementIn;
import com.arka.dto.out.CreateInventoryMovementOut;
import com.arka.gateway.repository.inventory.WarehouseInventoryGateway;
import com.arka.mapper.InventoryMovementMapper;
import com.arka.mapper.InventoryMovementMapperImpl;
import com.arka.model.Employee;
import com.arka.model.Warehouse;
import com.arka.enums.InventoryMovementType;
import com.arka.model.inventory.WarehouseInventory;
import com.arka.model.product.Product;
import com.arka.service.EmployeeService;
import com.arka.service.ProductService;
import com.arka.service.WarehouseInventoryService;
import com.arka.service.WarehouseService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterInventoryMovementUseCase {

    private final InventoryMovementMapper movementMapper =
            new InventoryMovementMapperImpl();

    private final WarehouseInventoryGateway inventoryGateway;

    private final ProductService productService;
    private final EmployeeService employeeService;
    private final WarehouseService warehouseService;
    private final WarehouseInventoryService inventoryService;

    public CreateInventoryMovementOut execute(CreateInventoryMovementIn input) {

        NullValidator.validate(input, "input");

        Warehouse foundWarehouse = warehouseService.findById(input.warehouseId());
        Employee foundEmployee = employeeService.findById(input.employeeId());
        Product foundProduct = productService.findById(input.productId());

        WarehouseInventory inventory = inventoryService.findByProductAndWarehouse(
                foundProduct.getId(),
                foundWarehouse.getId()
        );

        if(input.type().equals(InventoryMovementType.IN))
            inventory.addStock(input.quantity(), foundEmployee);
        else
            inventory.removeStock(input.quantity(), foundEmployee);


        inventoryGateway.save(inventory);

        return movementMapper.toOut(inventory.getInventoryMovements().peek());
    }
}
