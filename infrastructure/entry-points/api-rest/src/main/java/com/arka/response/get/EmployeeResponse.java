package com.arka.response.get;

public record EmployeeResponse(
        Long id,
        int code,
        String fullName,
        String position,
        String email
) {
}
