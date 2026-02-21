package com.arka.util.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@Getter
@RequiredArgsConstructor
public enum ExportFormat {

    CSV(MediaType.TEXT_PLAIN);

    private final MediaType mediaType;

}
