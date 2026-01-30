package com.arka.response;


import java.time.Instant;
import java.util.List;
import java.util.Set;


public record CompanyResponse(
        Long id,
        String name,
        Instant createdAt,
        List<ContactResponse> contacts,
        Set<ProductCategoryResponse> productCategories
){

}
