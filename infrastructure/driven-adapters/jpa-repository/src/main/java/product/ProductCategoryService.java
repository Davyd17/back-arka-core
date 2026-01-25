package product;

import com.arka.entities.product.ProductCategory;
import com.arka.gateway.ProductCategoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService implements ProductCategoryGateway {

    private final ProductCategoryRepository repository;
    private final ProductCategoryMapper mapper;

    @Override
    public Optional<ProductCategory> findProductCategoryByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDomain);
    }
}
