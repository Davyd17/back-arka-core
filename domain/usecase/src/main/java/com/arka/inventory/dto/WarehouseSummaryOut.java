package com.arka.inventory.dto;

import com.arka.model.information.Address;

public record WarehouseSummaryOut(

        Long id,
        Address address
) {
}
