package com.arka.controller.product;

import com.arka.model.product.ProductCategory;
import com.arka.usecase.ListProductCategoriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ListProductCategoriesUseCase listCategoriesUseCase;

    @GetMapping
    public List<ProductCategory> listAll(){
        return listCategoriesUseCase.execute();
    }
}
