package com.arka.request;

import com.arka.dto.in.SlugProductCategoryIn;
import com.arka.exceptions.Required;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;

public record CreateProductRequest (

        @Required(field = "sku")
        String sku,

        @Required(field = "id")
        String name,

        @Required(field = "description")
        String description,

        @Required(field = "base price")
        @DecimalMin(value = "0.00", inclusive = false)
        @Digits(integer = 10, fraction = 2)
        BigDecimal basePrice,

        Map<String, Object> attributes,

        @NotNull(message = "Missing required field: category slug")
        SlugProductCategoryIn slugCategory
){

}
