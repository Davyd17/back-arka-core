package com.arka.response.get;


public record ContactResponse(
        Long id,
        String name,
        String lastName,
        String position,
        String email
) {

}
