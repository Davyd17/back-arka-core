package com.arka.entities;

import com.arka.entities.product.ProductCategory;
import com.arka.enums.CompanyRelationType;
import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.NotFoundException;
import com.arka.util.NullValidator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Company {
    private Long id;
    private String name;
    private CompanyRelationType relation;
    private List<ProductCategory> productCategories;

    public static Company createSupplier(
            String name,
            CompanyRelationType relation,
            List<ProductCategory> categories
    ) {

        if (categories == null || categories.isEmpty())
            throw new IllegalArgumentException(
                    "Supplier should have at least one category");


        Company company = Company.builder()
                .name(name)
                .relation(relation)
                .productCategories(new ArrayList<>())
                .build();

        categories.forEach(company::addProductCategory);

        return company;
    }

    public void addProductCategory(ProductCategory category) {
        validateCategoryDuplication(category.getId());
        this.productCategories.add(category);
    }

    private void validateCategoryDuplication(Long categoryId) {
        boolean alreadyExists = this.productCategories.stream()
                .anyMatch(pc -> pc.getId().equals(categoryId));

        if (alreadyExists)
            throw new AlreadyExistsException(ProductCategory.class, categoryId);
    }

    public void removeProductCategory(Long categoryId) {

        boolean removed = this.productCategories.removeIf(pc ->
                categoryId.equals(pc.getId()));

        if (!removed)
            throw new NotFoundException(
                    String.format("Product category with id [%s] not found", categoryId)
            );
    }

    public void updateName(String newName) {
        NullValidator.validate(newName, "newName");
        this.name = newName;
    }

    public void updateRelation(CompanyRelationType relation) {
        NullValidator.validate(relation, "relation");
        this.relation = relation;
    }

}
