package com.arka.report.gateway;

import com.arka.report.ExportFormat;

public interface ExportGateway {

    <T> byte[] export(T data, ExportFormat format);
}
