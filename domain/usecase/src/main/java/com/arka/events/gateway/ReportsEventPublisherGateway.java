package com.arka.events.gateway;

import com.arka.events.dto.LowStockReportRequestedEvent;
import com.arka.events.dto.SalesReportRequestedEvent;

public interface ReportsEventPublisherGateway {

    void publishSalesReport(SalesReportRequestedEvent event);

    void publishLowStockReport(LowStockReportRequestedEvent event);
}
