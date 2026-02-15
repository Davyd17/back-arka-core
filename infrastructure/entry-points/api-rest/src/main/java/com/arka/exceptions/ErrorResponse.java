package com.arka.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse (
        String code,
        String message,
        LocalDateTime timestamp
){
}
