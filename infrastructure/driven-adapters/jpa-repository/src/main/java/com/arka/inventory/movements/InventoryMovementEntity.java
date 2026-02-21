package com.arka.inventory.movements;

import com.arka.employee.EmployeeEntity;
import com.arka.enums.InventoryMovementType;
import com.arka.product.ProductEntity;
import com.arka.warehouse.WarehouseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_movements")
public class InventoryMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryMovementType type;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int previousStock;

    @Column(nullable = false)
    @Min(value = 0, message = "New stock must be greater than 0")
    private int newStock;

    private String notes;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant registeredAt;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WarehouseEntity warehouse;


}
