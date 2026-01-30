package com.arka.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query(value = """
            SELECT c.*
            FROM companies c
            JOIN companies_product_categories cpc
                    ON cpc.company_id = c.id
            WHERE (cpc.product_category_id = :categoryId
                    AND c.relation = 'SUPPLIER')
            """,
            nativeQuery = true
    )
    List<CompanyEntity> getSuppliersByProductCategoryId(@Param("categoryId") Long categoryId);
}
