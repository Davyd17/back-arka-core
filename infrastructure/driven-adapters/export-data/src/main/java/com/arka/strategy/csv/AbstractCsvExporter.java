package com.arka.strategy.csv;

import com.arka.ReportExporter;
import com.arka.report.ExportFormat;

public abstract class AbstractCsvExporter<T> implements ReportExporter<T> {

    @Override
    public ExportFormat getFormat(){
        return ExportFormat.CSV;
    }

    protected String escape(String value) {
        if (value == null) return "";

        boolean containsSpecialChar =
                value.contains(",") ||
                        value.contains("\"") ||
                        value.contains("\n") ||
                        value.contains("\r");

        if (containsSpecialChar) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }
}
