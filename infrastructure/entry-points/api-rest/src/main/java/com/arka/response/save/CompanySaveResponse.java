package com.arka.response.save;


import com.arka.response.get.ContactResponse;
import com.arka.response.get.ProductCategoryResponse;

import java.time.Instant;
import java.util.List;
import java.util.Set;


public record CompanySaveResponse(
        Long id,
        String name,
        List<ContactSaveResponse> contacts,
        Set<ProductCategorySaveResponse> productCategories
){

}
