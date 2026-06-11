package com.arka.strategy.csv;

import com.arka.report.dto.LowStockItem;
import com.arka.report.dto.LowStockReportData;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class LowStockReportCsvExporter
        extends AbstractCsvExporter<LowStockReportData> {

    @Override
    public Class<LowStockReportData> getDataType() {
        return LowStockReportData.class;
    }

    public byte[] export(LowStockReportData report) {

        StringBuilder csvBuilder = new StringBuilder();

        //Set delimiter
        csvBuilder.append("sep=,\n");

        // Add CSV header
        csvBuilder.append("name,sku,category,stock\n");

        for(LowStockItem item : report.items()){

            csvBuilder.append(escape(item.product().name())).append(",");
            csvBuilder.append(escape(item.product().sku())).append(",");
            csvBuilder.append(escape(item.product().category())).append(",");
            csvBuilder.append(item.stock()).append("\n");

        }

        return ("\uFEFF" + csvBuilder).getBytes(StandardCharsets.UTF_8);
    }
}
