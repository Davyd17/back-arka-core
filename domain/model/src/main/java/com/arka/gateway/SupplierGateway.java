package com.arka.gateway;

import com.arka.entities.party.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierGateway {

    List<Supplier> GetSuppliersByProductCategoryId(Long categoryId);
}
