package com.arka.dto.in;

import java.util.Set;

public record CreateSupplierIn(
        String name,
        Set<Long> contactIds,
        Set<Long> productCategoryIds
) {
}
