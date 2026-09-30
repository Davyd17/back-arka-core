package com.arka.mapper;

import com.arka.events.dto.SalesReportRequestedEvent;
import com.arka.report.dto.SalesReportCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesReportEventMapper {

    SalesReportCommand toCommand(SalesReportRequestedEvent event);
}
