package com.arka.inventory.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WarehouseInventoryRepository
        extends JpaRepository<WarehouseInventoryEntity, Long> {


    @Query("""
            SELECT i FROM WarehouseInventoryEntity i
            WHERE i.product.id = :productId AND i.warehouse.id = :warehouseId
            """)
    Optional<WarehouseInventoryEntity>
    findByProductIdAndWarehouseId(
            @Param("productId") Long productId,
            @Param("warehouseId") Long warehouseId);


    @Query("""
        SELECT
            i.product.id AS productId,
            i.stock AS stock,
            i.product.name AS productName,
            i.product.sku AS sku,
            i.product.category.name AS productCategory
        FROM WarehouseInventoryEntity i
        WHERE i.warehouse.id = :warehouseId AND i.stock <= :threshold
        """)
    List<LowStockInventoryView> findLowStockInventoryByWarehouseId(
            @Param("warehouseId") Long warehouseId,
            @Param("threshold") int threshold);

    @Query("""
            SELECT SUM(i.stock) FROM WarehouseInventoryEntity i
            WHERE i.product.id = :productId
            """)
    int getTotalStockByProductId(@Param("productId") Long productId);
}
