package com.arka.service.util;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class DateRangeValidator {

    public static void validate(LocalDate start, LocalDate end) {

        if (start != null && end != null) {

            if (start.isAfter(end)) {
                throw new IllegalArgumentException("Since date must be before until date");
            }

            if (end.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Until date cannot be in the future");
            }
        } else {
            throw new IllegalArgumentException("Since and until dates must be provided");
        }
    }
}
