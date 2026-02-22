package com.arka.model;

import com.arka.enums.CompanyRelationType;
import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Company {
    private Long id;
    private String name;
    private CompanyRelationType relation;
    private Instant createdAt;
    private List<Contact> contacts;
    private Set<ProductCategory> productCategories;

    public static Company create(
            String name,
            CompanyRelationType relation,
            List<Contact> contacts,
            Set<ProductCategory> productCategories
    ){
        return Company.builder()
                .name(name)
                .relation(relation)
                .createdAt(Instant.now())
                .contacts(List.copyOf(contacts))
                .productCategories(Set.copyOf(productCategories))
                .build();
    }

    public void asCustomer(){
        this.relation = CompanyRelationType.CUSTOMER;
    }

    public void asSupplier(){
        this.relation = CompanyRelationType.SUPPLIER;
    }
}
