package com.arka.dto.out;

import com.arka.model.enums.CompanyRelationType;

public record CompanyCreateOrderOut (

        String name,
        CompanyRelationType relation

){
}
