package com.arka.inventory;

import com.arka.inventory.dto.CreateInventoryMovementIn;
import com.arka.inventory.dto.CreateInventoryMovementOut;
import com.arka.gateway.inventory.WarehouseInventoryGateway;
import com.arka.inventory.mapper.InventoryMovementMapper;
import com.arka.mapper.InventoryMovementMapperImpl;
import com.arka.model.Employee;
import com.arka.enums.InventoryMovementType;
import com.arka.model.inventory.WarehouseInventory;
import com.arka.party.service.EmployeeService;
import com.arka.inventory.service.WarehouseInventoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterInventoryMovementUseCase {

    private final InventoryMovementMapper movementMapper =
            new InventoryMovementMapperImpl();

    private final WarehouseInventoryGateway inventoryGateway;

    private final EmployeeService employeeService;
    private final WarehouseInventoryService inventoryService;

    public CreateInventoryMovementOut execute(CreateInventoryMovementIn input) {

        NullValidator.validate(input, "input");

        Employee foundEmployee = employeeService.findById(input.employeeId());

        WarehouseInventory inventory = inventoryService.findByProductAndWarehouse(
                input.productId(),
                input.warehouseId()
        );

        if(input.type().equals(InventoryMovementType.IN))
            inventory.addStock(input.quantity(), foundEmployee);
        else
            inventory.removeStock(input.quantity(), foundEmployee);


        inventoryGateway.save(inventory);

        return movementMapper.toOut(inventory.getInventoryMovements().peek());
    }
}
