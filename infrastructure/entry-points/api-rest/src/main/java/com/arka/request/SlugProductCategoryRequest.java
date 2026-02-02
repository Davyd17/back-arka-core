package com.arka.request;

import com.arka.exceptions.Required;

public record SlugProductCategoryRequest(

        @Required(field = "Category slug")
        String slug
) {
}
