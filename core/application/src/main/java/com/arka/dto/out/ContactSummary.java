package com.arka.dto.out;

public record ContactSummary(

        Long id,
        Long userId,
        String name,
        String lastName,
        String position,
        String email

) {
}
