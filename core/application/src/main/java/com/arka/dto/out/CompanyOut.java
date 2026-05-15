package com.arka.dto.out;

import com.arka.enums.CompanyRelationType;

import java.util.List;

public record CompanyOut(

        Long id,
        String name,
        CompanyRelationType relation,
        List<ProductCategoryOut> productCategories,
        List<ContactOut> contacts
){
}
