package com.arka.controller;

import com.arka.mappers.request.CompanyRequestMapper;
import com.arka.mappers.response.CompanySaveResponseMapper;
import com.arka.model.Company;
import com.arka.request.CreateCompanyRequest;
import com.arka.response.save.CompanySaveResponse;
import com.arka.usecase.CreateSupplierUseCase;
import com.arka.usecase.ListSuppliersByCategoryUseCase;
import com.arka.mappers.response.CompanyResponseMapper;
import com.arka.response.get.CompanyResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
@RequiredArgsConstructor
@Validated
public class SupplierController {

    private final ListSuppliersByCategoryUseCase listSupplierByCategory;
    private final CreateSupplierUseCase createSupplierUseCase;

    private final CompanyResponseMapper responseMapper;
    private final CompanyRequestMapper requestMapper;
    private final CompanySaveResponseMapper saveResponseMapper;



    @GetMapping("/categories/{slug}")
    public List<CompanyResponse> listByCategorySlug(@PathVariable @NotBlank String slug) {

        return listSupplierByCategory.execute(slug)
                        .stream()
                        .map(responseMapper::toResponse)
                        .toList();
    }

    @PostMapping
    public ResponseEntity<CompanySaveResponse> save(@Valid @RequestBody CreateCompanyRequest request){

        Company savedSupplier = createSupplierUseCase
                .execute(requestMapper.toDomain(request));

        URI uri = URI.create(Long.toString(savedSupplier.getId()));

        return ResponseEntity.created(uri).body(
                saveResponseMapper.toSaveResponse(savedSupplier));
    }

}
