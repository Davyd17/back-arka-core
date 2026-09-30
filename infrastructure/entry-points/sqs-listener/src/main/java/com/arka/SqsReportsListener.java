package com.arka;

import com.arka.events.dto.LowStockReportRequestedEvent;
import com.arka.mapper.LowStockReportEventMapper;
import com.arka.mapper.SalesReportEventMapper;
import com.arka.notification.SendWeeklyLowStockReportUseCase;
import com.arka.notification.SendWeeklySalesReportUseCase;
import com.arka.events.dto.SalesReportRequestedEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SqsReportsListener {

    private final LowStockReportEventMapper lowStockReportMapper;
    private final SalesReportEventMapper salesReportEventMapper;

    private final SendWeeklySalesReportUseCase salesReportUseCase;
    private final SendWeeklyLowStockReportUseCase lowStockReportUseCase;

    @SqsListener("${cloud-provider.aws.sqs.sales-report-queue}")
    public void handleSalesReportEvent(SalesReportRequestedEvent event){

        log.info("Received SalesReportRequestedEvent from SQS for email: {}",
                event.recipient());

        salesReportUseCase.execute(salesReportEventMapper.toCommand(event));

        log.info("Successfully processed week sales report task for: {}",
                event.recipient());
    }

    @SqsListener("${cloud-provider.aws.sqs.low-stock-report-queue}")
    public void handleLowStockReportEvent(LowStockReportRequestedEvent event){

        log.info("Received LowStockReportRequestedEvent from SQS for email: {}",
                event.recipient());

        lowStockReportUseCase.execute(lowStockReportMapper.toCommand(event));

        log.info("Successfully processed low stock report task for: {}",
                event.recipient());
    }

}
