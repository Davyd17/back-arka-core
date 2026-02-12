package com.arka.controller;

import com.arka.mappers.WarehouseInventoryRestMapper;
import com.arka.response.get.WarehouseInventoryResponse;
import com.arka.usecase.GenerateLowStockReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/warehouses-inventory")
@RequiredArgsConstructor
public class WarehouseInventoryController {

    private final GenerateLowStockReportUseCase generateLowStockReportUseCase;

    private final WarehouseInventoryRestMapper mapper;

    @GetMapping("/low-stock/{warehouseInventoryId}")
    public final List<WarehouseInventoryResponse> getLowStockReport(
            @PathVariable Long warehouseInventoryId, int threshold){

        return generateLowStockReportUseCase.execute(warehouseInventoryId, threshold)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

}
