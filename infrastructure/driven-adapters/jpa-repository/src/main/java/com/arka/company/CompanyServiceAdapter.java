package com.arka.company;

import com.arka.model.Company;
import com.arka.gateway.SupplierGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceAdapter implements SupplierGateway {

    private final CompanyRepository repository;
    private final CompanyEntityMapper mapper;

    @Override
    public List<Company> getSuppliersByProductCategoryId(Long categoryId) {

        List<CompanyEntity> foundSuppliers =
                repository.getSuppliersByProductCategoryId(categoryId);

        if(!foundSuppliers.isEmpty()) {
            return foundSuppliers.stream()
                    .map(mapper::toDomain)
                    .toList();
        } return List.of();
    }
}
