package party.supplier;

import com.arka.entities.party.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = """
            SELECT s.*
            FROM suppliers s
            JOIN suppliers_product_categories spc
                    ON spc.supplier_id = s.id
            WHERE spc.product_category_id = :categoryId
            """,
            nativeQuery = true
    )
    List<SupplierEntity> GetSuppliersByProductCategoryId(Long categoryId);
}
