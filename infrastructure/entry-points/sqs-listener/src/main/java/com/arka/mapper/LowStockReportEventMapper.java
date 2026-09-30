package com.arka.mapper;

import com.arka.events.dto.LowStockReportRequestedEvent;
import com.arka.report.dto.LowStockReportCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LowStockReportEventMapper {

    LowStockReportCommand toCommand(LowStockReportRequestedEvent requestedEvent);
}
