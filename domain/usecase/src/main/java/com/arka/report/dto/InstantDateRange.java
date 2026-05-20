package com.arka.report.dto;

import java.time.Instant;

public record InstantDateRange(
        Instant start,
        Instant end
) {
}
