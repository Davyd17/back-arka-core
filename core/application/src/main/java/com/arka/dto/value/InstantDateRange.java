package com.arka.dto.value;

import java.time.Instant;

public record InstantDateRange(
        Instant start,
        Instant end
) {
}
