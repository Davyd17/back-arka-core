package com.arka.mapper;

import com.arka.dto.out.EmployeeOut;
import com.arka.model.Employee;
import com.arka.model.information.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EmployeeMapper {

    @Mappings({
            @Mapping(target = "fullName", source = "contact"),
            @Mapping(target = "email", source = "contact.email"),
            @Mapping(target = "position", source = "contact.position")
    })
    EmployeeOut toOut(Employee domain);

    default String toFullName(Contact contact){
        return contact.getName() + " " + contact.getLastName();
    }
}
