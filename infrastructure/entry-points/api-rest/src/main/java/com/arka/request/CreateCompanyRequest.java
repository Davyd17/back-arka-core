package com.arka.request;

import com.arka.exceptions.Required;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateCompanyRequest(

        @Schema(description = "Company legal name",
                example = "TechImports S.A.S.")
        @Required(field = "name") String name,

        @ArraySchema(
                schema = @Schema(description = "Associated contact ID", example = "10"),
                minItems = 1
        )
        @NotEmpty(message = "There must be at least one contact")
        List<Long> contactIds,

        @ArraySchema(
                schema = @Schema(description = "Product category ID assigned to supplier", example = "3"),
                minItems = 1)
        @NotEmpty(message =
                "There must be at least one registered category")
        List<Long> productCategoryIds

) {

}
