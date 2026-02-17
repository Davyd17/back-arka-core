package com.arka.dto.in;

import java.util.Set;

public record UpdateOrderIn(

        Long id,
        String notes,
        Set<UpdateOrderItemIn>items
) {
}
