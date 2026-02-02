package com.arka.response.save;


import java.util.List;
import java.util.Set;


public record CreateCompanyResponse(
        Long id,
        String name,
        List<ContactSaveResponse> contacts,
        Set<CreateProductCategoryResponse> productCategories
){

}
