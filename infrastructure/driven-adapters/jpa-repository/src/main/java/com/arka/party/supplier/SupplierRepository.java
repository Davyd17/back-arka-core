package com.arka.party.supplier;

import com.arka.entities.party.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    @Query(value = """
            SELECT s.*
            FROM suppliers s
            JOIN suppliers_product_categories spc
                    ON spc.supplier_id = s.supplier_id
            WHERE spc.product_category_id = :categoryId
            """,
            nativeQuery = true
    )
    List<SupplierEntity> getSuppliersByProductCategoryId(@Param("categoryId") Long categoryId);
}
