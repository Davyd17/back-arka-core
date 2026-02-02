package com.arka.dto.in;

import com.arka.model.enums.CompanyRelationType;
import com.arka.model.information.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
public class CreateCompany {

    private String name;
    private CompanyRelationType relation;
    private List<Contact> contacts;
    private Set<SlugProductCategory> slugProductCategories;
    private Instant createdAt;

}
