package com.arka.util.export;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The Context class in the Strategy Pattern architecture for report exportation.
 * <p>
 * This service automatically collects all Spring-managed {@link ReportExporter} beans
 * via dependency injection. At runtime, it acts as a dynamic router, evaluating incoming
 * requests and delegating the export execution to the matching concrete strategy.
 * </p>
 * * @see ReportExporter
 * @see ExportFormat
 */

@RequiredArgsConstructor
@Component
public class ExportService {

    /**
     * Internal registry of all available export strategies detected by the Spring container.
     */
    private final List<ReportExporter<?>> exporters;

    @SuppressWarnings("unchecked")
    public <T> byte[] export(ExportFormat format, T data) {

        ReportExporter<T> strategy = (ReportExporter<T>) exporters.stream()
                .filter(e -> e.getFormat() == format)
                .filter(e -> e.getDataType().isAssignableFrom(data.getClass()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No exporter found for format " + format +
                                        " and type " + data.getClass().getSimpleName()
                        )
                );

        return strategy.export(data);
    }
}
