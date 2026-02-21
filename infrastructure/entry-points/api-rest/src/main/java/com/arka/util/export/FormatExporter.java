package com.arka.util.export;

public interface FormatExporter<T> {

    ExportFormat getFormat();

    Class<T> getDataType();

    byte[] export(T data);
}
