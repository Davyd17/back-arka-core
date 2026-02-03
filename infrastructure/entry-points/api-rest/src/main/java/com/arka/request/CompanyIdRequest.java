package com.arka.request;

import com.arka.exceptions.Required;

public record CompanyIdRequest (

        @Required(field = "id")
        Long id
){
}
