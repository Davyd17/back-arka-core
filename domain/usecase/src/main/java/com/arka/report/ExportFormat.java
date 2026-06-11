package com.arka.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExportFormat {

    CSV("text/csv;charset=UTF-8", "csv");

    private final String mimeType;
    private final String fileExtension;
}
