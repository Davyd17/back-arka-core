package com.arka.util.export;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ExportService {

    private final List<FormatExporter<?>> exporters;

    @SuppressWarnings("unchecked")
    public <T> byte[] export(ExportFormat format, T data) {

        FormatExporter<T> exporter = (FormatExporter<T>) exporters.stream()
                .filter(e -> e.getFormat() == format)
                .filter(e -> e.getDataType().isAssignableFrom(data.getClass()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No exporter found for format " + format +
                                        " and type " + data.getClass().getSimpleName()
                        )
                );

        return exporter.export(data);
    }
}
