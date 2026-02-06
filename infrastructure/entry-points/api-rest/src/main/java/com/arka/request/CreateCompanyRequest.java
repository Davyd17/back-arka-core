package com.arka.request;

import com.arka.exceptions.Required;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateCompanyRequest(

        @Required(field = "id") String name,

        @NotEmpty(message =
                "There must be at least one contact")
        List<CreateContactRequest> contacts,

        @NotEmpty(message =
                "There must be at least one registered category")
        List<SlugProductCategoryRequest> slugProductCategories

) {

}
