package com.arka.model;

import com.arka.enums.CompanyRelationType;
import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.NotFoundException;
import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Company {
    private Long id;
    @Setter private String name;
    @Setter private CompanyRelationType relation;
    private List<Contact> contacts;
    private Set<ProductCategory> productCategories;

    public static Company create(
            String name,
            CompanyRelationType relation,
            List<Contact> contacts
    ) {

        if(contacts == null || contacts.isEmpty())
            throw new IllegalArgumentException(
                    "Company should have at least one contact");

        return Company.builder()
                .name(name)
                .relation(relation)
                .contacts(new ArrayList<>(contacts))
                .productCategories(new HashSet<>())
                .build();
    }

    public void addContact(Contact newContact) {

        this.validateContactAlreadyExists(newContact.getId());
            this.contacts.add(newContact);
    }

    private void validateContactAlreadyExists(Long contactId){

        boolean alreadyExists = this.contacts.stream()
                .anyMatch(contact ->
                        contact.getId().equals(contactId));

        if (alreadyExists){
            throw new AlreadyExistsException("Contact", contactId);
        }

    }

    public void removeContact(Long contactId) {

        boolean removed = this.contacts.removeIf(c ->
                contactId.equals(c.getId()));

        if (!removed)
            throw new NotFoundException(
                    String.format("Contact with id [%s] not found", contactId)
            );
    }

    public void addProductCategory(ProductCategory category) {
        this.validateCategoryAlreadyExists(category.getId());
        this.productCategories.add(category);
    }

    private void validateCategoryAlreadyExists(Long categoryId) {
        boolean alreadyExists = this.productCategories.stream()
                .anyMatch(pc -> pc.getId().equals(categoryId));

        if (alreadyExists)
            throw new AlreadyExistsException("ProductCategory", categoryId);
    }

    public void removeProductCategory(Long categoryId) {

        boolean removed = this.productCategories.removeIf(pc ->
                categoryId.equals(pc.getId()));

        if (!removed)
            throw new NotFoundException(
                    String.format("Product category with id [%s] not found", categoryId)
            );
    }

}
