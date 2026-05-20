package com.arka.party.dto;

public record EmployeeOut(
        Long id,
        int code,
        String fullName,
        String position,
        String email
) {

}
