package com.arka.company;

import com.arka.model.Company;
import com.arka.gateway.party.SupplierGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceAdapter implements SupplierGateway {

    private final SupplierRepository repository;
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

    @Override
    public Company getCompanyById(Long id) {
        return mapper.toDomain(repository.getSupplierById(id));
    }

    @Override
    public Company createCompany(Company supplier) {

        CompanyEntity savedEntity = repository
                .save(mapper.toEntity(supplier));

        return mapper.toDomain(savedEntity);
    }
}
