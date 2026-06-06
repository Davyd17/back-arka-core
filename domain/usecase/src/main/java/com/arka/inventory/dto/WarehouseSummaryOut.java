package com.arka.inventory.dto;


import com.arka.entities.information.Address;

public record WarehouseSummaryOut(

        Long id,
        Address address
) {
}
