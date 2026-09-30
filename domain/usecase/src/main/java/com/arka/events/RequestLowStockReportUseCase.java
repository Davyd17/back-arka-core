package com.arka.events;

import com.arka.events.gateway.ReportsEventPublisherGateway;
import com.arka.events.mapper.LowStockReportEventMapper;
import com.arka.report.dto.LowStockReportCommand;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
public class RequestLowStockReportUseCase {

    private final ReportsEventPublisherGateway reportsEventPublisherGateway;
    private final LowStockReportEventMapper mapper =
            Mappers.getMapper(LowStockReportEventMapper.class);

    public void execute(LowStockReportCommand command){
        NullValidator.validate(command, "event");
        reportsEventPublisherGateway.publishLowStockReport(mapper.toEvent(command));
    }
}
