package com.arka.controller;

import com.arka.usecase.ListSuppliersByCategoryUseCase;
import com.arka.mappers.SupplierResponseMapper;
import com.arka.response.SupplierResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
@RequiredArgsConstructor
@Validated
public class SupplierController {

    private final ListSuppliersByCategoryUseCase listSupplierByCategory;
    private final SupplierResponseMapper mapper;

    @GetMapping("/categories/{slug}")
    public List<SupplierResponse> listByCategorySlug(@PathVariable @NotBlank String slug) {

        return listSupplierByCategory.execute(slug)
                        .stream()
                        .map(mapper::toResponse)
                        .toList();
    }

}
