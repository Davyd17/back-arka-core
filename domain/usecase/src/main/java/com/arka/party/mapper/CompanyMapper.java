package com.arka.party.mapper;

import com.arka.entities.Company;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.party.dto.CompanyOut;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {

    CompanyOut toOut(Company domain);

    UpdateOrderOut.OrderCompany toOderCompany(Company domain);
}
