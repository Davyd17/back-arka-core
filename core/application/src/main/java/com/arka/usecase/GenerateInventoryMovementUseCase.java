package com.arka.usecase;

import com.arka.dto.in.CreateInventoryMovementIn;
import com.arka.gateway.repository.inventory.InventoryMovementGateway;
import com.arka.gateway.repository.inventory.WarehouseInventoryGateway;
import com.arka.mapper.InventoryMovementMapper;
import com.arka.mapper.InventoryMovementMapperImpl;
import com.arka.model.Employee;
import com.arka.model.Warehouse;
import com.arka.enums.InventoryMovementType;
import com.arka.model.inventory.InventoryMovement;
import com.arka.model.inventory.WarehouseInventory;
import com.arka.model.product.Product;
import com.arka.service.EmployeeService;
import com.arka.service.ProductService;
import com.arka.service.WarehouseInventoryService;
import com.arka.service.WarehouseService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class GenerateInventoryMovementUseCase {

    private final InventoryMovementMapper mapper =
            new InventoryMovementMapperImpl();

    private final InventoryMovementGateway gateway;
    private final WarehouseInventoryGateway inventoryGateway;

    private final ProductService productService;
    private final EmployeeService employeeService;
    private final WarehouseService warehouseService;
    private final WarehouseInventoryService inventoryService;

    public InventoryMovement execute(CreateInventoryMovementIn request) {

        if (Objects.nonNull(request)) {

            Warehouse foundWarehouse = warehouseService.findById(request.warehouseId());
            Employee foundEmployee = employeeService.findById(request.employeeId());
            Product foundProduct = productService.findById(request.productId());


            InventoryMovement newMovement = mapper.toDomain(request)
                    .toBuilder()
                    .product(foundProduct)
                    .employee(foundEmployee)
                    .warehouse(foundWarehouse)
                    .build();

            WarehouseInventory inventory = inventoryService.findByProductAndWarehouse(
                    foundProduct.getId(),
                    foundWarehouse.getId()
            );

            int currentStock = inventory.getStock();

            int movementResult;

            if(Objects.equals(newMovement.getType(), InventoryMovementType.IN)){

                movementResult = currentStock + newMovement.getQuantity();

            } else {

                movementResult = currentStock - newMovement.getQuantity();
            }


            newMovement.toBuilder()
                    .previousStock(currentStock)
                    .build();

            inventoryGateway.save(
                    inventory.toBuilder()
                    .stock(movementResult)
                    .build());

            return gateway.save(
                    newMovement.toBuilder()
                            .previousStock(currentStock)
                            .newStock(movementResult)
                    .build());

        } else throw new IllegalArgumentException(
                "Request inventory movement can't be null"
        );
    }
}
