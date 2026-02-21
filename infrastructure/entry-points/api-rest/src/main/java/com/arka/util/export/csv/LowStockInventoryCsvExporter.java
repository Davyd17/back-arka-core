package com.arka.util.export.csv;

import com.arka.dto.out.LowStockReportOut;
import com.arka.dto.value.LowStockItem;
import com.arka.util.export.ExportFormat;
import com.arka.util.export.FormatExporter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class LowStockInventoryCsvExporter
        implements FormatExporter<LowStockReportOut> {

    @Override
    public ExportFormat getFormat() {
        return ExportFormat.CSV;
    }

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

    private static String escape(String value){

        if(value == null) {
            return "";
        }

        if (value.contains(",") || value.contains("\"")){
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        } return value;
    }
}
