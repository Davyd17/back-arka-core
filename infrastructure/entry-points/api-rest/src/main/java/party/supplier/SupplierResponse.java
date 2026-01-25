package party.supplier;


import information.ContactResponse;
import product.ProductCategoryResponse;

import java.time.Instant;
import java.util.Set;

public class SupplierResponse {

    private Long supplierId;
    private Instant createdAt;
    private ContactResponse contact;
    private Set<ProductCategoryResponse> productCategories;
}
