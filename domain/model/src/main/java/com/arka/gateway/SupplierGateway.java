package com.arka.gateway;

import com.arka.entities.party.Supplier;

import java.util.List;

public interface SupplierGateway {

    List<Supplier> getSuppliersByProductCategoryId(Long categoryId);
}
