package com.arka.dto.in;

import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;

public record CreateOrderIn (

        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        CompanyIdIn companyId
){

}
