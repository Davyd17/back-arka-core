package com.arka.events.mapper;

import com.arka.events.dto.LowStockReportRequestedEvent;
import com.arka.report.dto.LowStockReportCommand;
import org.mapstruct.Mapper;

@Mapper
public interface LowStockReportEventMapper {

    LowStockReportRequestedEvent toEvent(LowStockReportCommand command);
}
