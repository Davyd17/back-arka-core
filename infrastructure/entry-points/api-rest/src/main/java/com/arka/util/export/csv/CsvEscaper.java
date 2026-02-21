package com.arka.util.export.csv;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvEscaper {

    public static String escape(String value) {
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
