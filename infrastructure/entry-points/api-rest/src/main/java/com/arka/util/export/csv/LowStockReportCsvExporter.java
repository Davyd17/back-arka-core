package com.arka.util.export.csv;

import com.arka.report.dto.LowStockReportOut;
import com.arka.report.dto.LowStockItem;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class LowStockReportCsvExporter
        extends AbstractCsvExporter<LowStockReportOut> {

    @Override
    public Class<LowStockReportOut> getDataType() {
        return LowStockReportOut.class;
    }

    public byte[] export(LowStockReportOut report) {

        StringBuilder csvBuilder = new StringBuilder();

        // Add CSV header
        csvBuilder.append("name,sku,category,stock\n");

        for(LowStockItem item : report.items()){

            csvBuilder.append(escape(item.product().name())).append(",");
            csvBuilder.append(escape(item.product().sku())).append(",");
            csvBuilder.append(escape(item.product().category())).append(",");
            csvBuilder.append(item.stock()).append("\n");

        }

        return csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
