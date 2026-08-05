package com.arka.party.dto;

public record CreateContactInput(
        String name,
        String lastName,
        String email
) {
}
