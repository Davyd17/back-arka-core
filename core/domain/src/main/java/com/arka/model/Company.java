package com.arka.model;

import com.arka.model.enums.CompanyRelationType;
import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    private Long id;
    private String name;
    private CompanyRelationType relation;
    private Instant createdAt;
    private List<Contact> contacts;
    private Set<ProductCategory> productCategories;
}
