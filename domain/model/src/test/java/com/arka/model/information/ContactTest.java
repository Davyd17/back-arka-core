package com.arka.model.information;

import com.arka.entities.Company;
import com.arka.entities.information.Address;
import com.arka.entities.information.Contact;
import com.arka.entities.information.PhoneNumber;
import com.arka.enums.AddressType;
import com.arka.enums.CompanyRelationType;
import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.InvalidActivationStateException;
import com.arka.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = Contact.create("John", "Doe", "john@test.com");
    }

    private Address buildAddress(Long id) {
        return new Address(id,
                "Country",
                "City",
                null,
                "Street",
                null,
                AddressType.CUSTOMER,
                true);
    }

    private PhoneNumber buildPhoneNumber(Long id) {
        return new PhoneNumber(id,
                "+57",
                null,
                "123456789",
                true,
                Instant.now());
    }
    
    private Company buildCompany(Long id){
        return new Company(
                id,
                "TestCompany",
                CompanyRelationType.CUSTOMER,
                new ArrayList<>());
    }

    // --- create ---

    @Test
    void shouldCreateContactAsActiveByDefault() {
        assertTrue(contact.isActive());
    }

    // --- activate / deactivate ---

    @Test
    void shouldThrowWhenActivatingAlreadyActiveContact() {
        assertThrows(InvalidActivationStateException.class,
                () -> contact.activate());
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveContact() {
        contact.deactivate();
        assertThrows(InvalidActivationStateException.class,
                () -> contact.deactivate());
    }

    // --- addresses ---

    @Test
    void shouldRemoveAddress(){
        contact.addAddress(buildAddress(1L));
        contact.removeAddress(1L);
        assertTrue(contact.getAddresses().isEmpty());
    }

    @Test
    void shouldThrowWhenAddingDuplicateAddress() {
        contact.addAddress(buildAddress(1L));
        assertThrows(AlreadyExistsException.class,
                () -> contact.addAddress(buildAddress(1L)));
    }

    @Test
    void shouldThrowWhenRemovingNonExistentAddress() {
        assertThrows(NotFoundException.class,
                () -> contact.removeAddress(99L));
    }

    // --- phone numbers ---
    @Test
    void shouldRemovePhoneNumber(){
        contact.addPhoneNumber(buildPhoneNumber(1L));
        contact.removePhoneNumber(1L);
        assertTrue(contact.getPhoneNumbers().isEmpty());
    }

    @Test
    void shouldThrowWhenAddingDuplicatePhoneNumber() {

        contact.addPhoneNumber(buildPhoneNumber(1L));
        assertThrows(AlreadyExistsException.class,
                () -> contact.addPhoneNumber(buildPhoneNumber(1L)));
    }

    @Test
    void shouldThrowWhenRemovingNonExistentPhoneNumber() {
        assertThrows(NotFoundException.class,
                () -> contact.removePhoneNumber(99L));
    }

    // -- belongs to company

    @Test
    void shouldAssingOne(){
        Company company = buildCompany(1L);
        contact.assignCompany(company, "Test Position");

        assertNotNull(contact.getCompany());
        assertThat(contact.getCompany().getId()).isEqualTo(1L);
        assertThat(contact.getCompanyPosition()).isEqualTo("Test Position");
    }
    
    @Test
    void shouldThrowWhenCompanyAlreadyAssigned(){
        Company company = buildCompany(1L);
        contact.assignCompany(company, "Test Position");

        Company company1 = buildCompany(2L);

        assertThrows(AlreadyExistsException.class,
                () -> contact.assignCompany(company1, "New Test Position"));
    }

    @Test
    void shouldReturnTrueIfHasCompanyAssigned(){
        Company company = buildCompany(1L);
        contact.assignCompany(company, "Test Position");

        assertThat(contact.hasCompanyAssigned()).isTrue();
    }

}
