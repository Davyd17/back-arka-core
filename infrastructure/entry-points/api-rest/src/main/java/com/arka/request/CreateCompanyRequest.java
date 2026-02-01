package com.arka.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateCompanyRequest(

        @NotBlank(message = "Missing name") String name,

        @NotEmpty(message =
                "There must be at least one contact")
        List<CreateContactRequest> contacts,

        @NotEmpty(message =
                "There must be at least one registered category")
        List<SlugProductCategoryRequest> slugProductCategories

) {

}
