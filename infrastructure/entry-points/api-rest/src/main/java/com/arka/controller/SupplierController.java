package com.arka.controller;

import com.arka.mappers.CompanyRestMapper;
import com.arka.party.dto.CompanyOut;
import com.arka.request.CreateCompanyRequest;
import com.arka.response.save.CreateCompanyResponse;
import com.arka.party.CreateSupplierUseCase;
import com.arka.party.ListSuppliersByCategoryUseCase;
import com.arka.response.get.CompanyResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
@RequiredArgsConstructor
@Validated
public class SupplierController {

    private final ListSuppliersByCategoryUseCase listSupplierByCategory;
    private final CreateSupplierUseCase createSupplierUseCase;

    private final CompanyRestMapper mapper;



    @GetMapping("/categories/{id}")
    public List<CompanyResponse> listById(@PathVariable @NotBlank Long id) {

        return listSupplierByCategory.execute(id)
                        .stream()
                        .map(mapper::toResponse)
                        .toList();
    }

    @PostMapping
    public ResponseEntity<CreateCompanyResponse> save(@Valid @RequestBody CreateCompanyRequest request){

        CompanyOut savedSupplier = createSupplierUseCase
                .execute(mapper.toInput(request));

        CreateCompanyResponse response = mapper.toCreateResponse(savedSupplier);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

}
