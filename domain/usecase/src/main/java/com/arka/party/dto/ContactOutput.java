package com.arka.party.dto;

public record ContactOutput(
        Long id,
        String name,
        String lastName,
        String email
) {
}
