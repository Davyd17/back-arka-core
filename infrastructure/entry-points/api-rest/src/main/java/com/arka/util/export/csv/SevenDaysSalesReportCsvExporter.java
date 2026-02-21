package com.arka.util.export.csv;

import com.arka.dto.out.CustomerSalesReportOut;
import com.arka.dto.out.ProductSalesReportOut;
import com.arka.dto.out.SalesReportOut;
import com.arka.util.export.ExportFormat;
import com.arka.util.export.FormatExporter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.arka.util.export.csv.CsvEscaper.escape;

@Component
public class SevenDaysSalesReportCsvExporter
        implements FormatExporter<SalesReportOut> {


    @Override
    public ExportFormat getFormat() {
        return ExportFormat.CSV;
    }

    @Override
    public Class<SalesReportOut> getDataType() {
        return SalesReportOut.class;
    }

    @Override
    public byte[] export(SalesReportOut data) {

        if (data == null) {
            return new byte[0];
        }

        StringBuilder csv = new StringBuilder();

        buildCsv(csv, data);

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void buildCsv(StringBuilder csv, SalesReportOut data){

        appendTopSellingProducts(csv, data.topSellingProducts());

        appendMostFrequentBuyers(csv, data.mostFrequentBuyers());

        appendTotalSales(csv,data.totalSales());

    }

    private void appendTotalSales(StringBuilder csv, BigDecimal totalSales) {
        csv.append("TOTAL SALES,");
        csv.append(totalSales).append("\n");
    }

    private void appendTopSellingProducts
            (StringBuilder csv, List<ProductSalesReportOut> products) {

        csv.append("TOP SELLING PRODUCTS\n");
        csv.append("SKU,Name,Category,Units Sold\n");

        for (ProductSalesReportOut product : products) {
            csv.append(escape(product.sku())).append(",")
                    .append(escape(product.name())).append(",")
                    .append(escape(product.category())).append(",")
                    .append(product.unitsSold())
                    .append("\n");
        }
    }

    private void appendMostFrequentBuyers
            (StringBuilder csv, List<CustomerSalesReportOut> customers) {

        csv.append("MOST FREQUENT BUYERS\n");
        csv.append("Company Name,Total Orders\n");

        for (CustomerSalesReportOut customer : customers) {
            csv.append(escape(customer.companyName())).append(",")
                    .append(customer.totalOrders())
                    .append("\n");
        }
    }


}
