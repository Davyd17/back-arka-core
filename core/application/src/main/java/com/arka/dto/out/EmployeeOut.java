package com.arka.dto.out;

public record EmployeeOut(
        Long id,
        int code,
        String fullName,
        String position,
        String email
) {

}
