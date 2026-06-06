package com.arka.party.dto;

import com.arka.product.dto.ProductCategoryOut;
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
