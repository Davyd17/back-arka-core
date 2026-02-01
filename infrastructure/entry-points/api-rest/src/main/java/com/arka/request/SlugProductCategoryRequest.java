package com.arka.request;

import jakarta.validation.constraints.NotBlank;

public record SlugProductCategoryRequest(

        @NotBlank(message = "Product category slug required")
        String slug
) {
}
