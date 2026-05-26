package com.arka.util.export.csv;

import com.arka.util.export.ExportFormat;
import com.arka.util.export.ReportExporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
