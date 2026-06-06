package com.arka.util.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@Getter
@RequiredArgsConstructor
public enum ExportFormat {

    CSV(MediaType.parseMediaType("text/csv; charset=UTF-8"));

    private final MediaType mediaType;

}
