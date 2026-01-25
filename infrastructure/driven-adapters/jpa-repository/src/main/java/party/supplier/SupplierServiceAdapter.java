package party.supplier;

import com.arka.entities.party.Supplier;
import com.arka.gateway.SupplierGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceAdapter implements SupplierGateway {

    private final SupplierRepository repository;
    private final SupplierEntityMapper mapper;

    @Override
    public List<Supplier> GetSuppliersByProductCategoryId(Long categoryId) {

        List<SupplierEntity> foundSuppliers =
                repository.GetSuppliersByProductCategoryId(categoryId);

        if(!foundSuppliers.isEmpty()) {
            return foundSuppliers.stream()
                    .map(mapper::toDomain)
                    .toList();
        } return List.of();
    }
}
