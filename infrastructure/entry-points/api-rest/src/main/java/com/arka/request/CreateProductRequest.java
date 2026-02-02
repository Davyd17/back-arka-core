package com.arka.request;

import com.arka.dto.in.SlugProductCategoryIn;
import com.arka.exceptions.Required;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record CreateProductRequest (

        @Required(field = "sku")
        String sku,

        @Required(field = "name")
        String name,

        @Required(field = "description")
        String description,

        Map<String, Object> attributes,

        @NotNull(message = "Missing required field: category slug")
        SlugProductCategoryIn slugCategory
){

}
