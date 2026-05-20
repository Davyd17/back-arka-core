package com.arka.party.dto;

import java.util.Set;

public record CreateSupplierIn(
        String name,
        Set<Long> contactIds,
        Set<Long> productCategoryIds
) {
}
