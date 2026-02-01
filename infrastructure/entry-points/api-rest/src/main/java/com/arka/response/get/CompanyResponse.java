package com.arka.response.get;


import java.time.Instant;
import java.util.List;
import java.util.Set;


public record CompanyResponse(
        Long id,
        String name,
        List<ContactResponse> contacts,
        Set<ProductCategoryResponse> productCategories,
        Instant createdAt
){

}
