package com.arka.service.export;

import com.arka.dto.out.LowStockItemOut;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class LowStockInventoryCsvExporterService{


    public static byte[] exportToCsv(List<LowStockItemOut> items) {

        StringBuilder csvBuilder = new StringBuilder();

        // Add CSV header
        csvBuilder.append("name,sku,category,stock\n");

        for(LowStockItemOut item : items){

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
