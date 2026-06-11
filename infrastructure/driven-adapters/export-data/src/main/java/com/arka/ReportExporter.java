package com.arka;

import com.arka.report.ExportFormat;

/**
 * Strategy interface for exporting domain-specific report data.
 * <p>
 * <b>How to extend:</b> To add a new export capability (e.g., Excel, PDF),
 * implement this interface, specify the target {@link ExportFormat} and data payload
 * class, and annotate the class with {@code @Component}. Spring will automatically
 * detect it and route traffic to it via the {@code ExportService}.
 * </p>
 */
public interface ReportExporter<T> {

    ExportFormat getFormat();

    Class<T> getDataType();

    byte[] export(T data);
}
