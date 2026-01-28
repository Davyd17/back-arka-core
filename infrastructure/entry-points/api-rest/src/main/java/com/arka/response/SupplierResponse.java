package com.arka.response;


import java.time.Instant;
import java.util.Set;


public record SupplierResponse (
        Long supplierId,
        Instant createdAt,
        ContactResponse contact,
        Set<ProductCategoryResponse> productCategories
){

}
