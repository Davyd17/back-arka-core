package com.arka.gateway;

import com.arka.model.party.Supplier;

import java.util.List;

public interface SupplierGateway {

    List<Supplier> getSuppliersByProductCategoryId(Long categoryId);
}
