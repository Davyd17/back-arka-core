package com.arka.model.information;

import com.arka.entities.information.Address;
import com.arka.entities.information.Contact;
import com.arka.entities.information.PhoneNumber;
import com.arka.enums.AddressType;
import com.arka.exceptions.AlreadyExistsException;
import com.arka.exceptions.InvalidActivationStateException;
import com.arka.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = Contact.create(
                "John", "Doe", "CEO", "john@test.com",
                new ArrayList<>(List.of(buildAddress(1L))),
                new ArrayList<>(List.of(buildPhoneNumber(1L))),
                null
        );
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

    // --- create ---

    @Test
    void shouldCreateContactAsActiveByDefault() {
        assertTrue(contact.isActive());
    }

    @Test
    void shouldThrowWhenAddressesIsNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                Contact.create("John", "Doe", "CEO", "john@test.com",
                        null, List.of(buildPhoneNumber(1L)), null));

        assertThrows(IllegalArgumentException.class, () ->
                Contact.create("John", "Doe", "CEO", "john@test.com",
                        new ArrayList<>(), List.of(buildPhoneNumber(1L)), null));
    }

    @Test
    void shouldThrowWhenPhoneNumbersIsNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                Contact.create("John", "Doe", "CEO", "john@test.com",
                        List.of(buildAddress(1L)), null, null));

        assertThrows(IllegalArgumentException.class, () ->
                Contact.create("John", "Doe", "CEO", "john@test.com",
                        List.of(buildAddress(1L)), new ArrayList<>(), null));
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
    void shouldThrowWhenAddingDuplicateAddress() {
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
    void shouldThrowWhenAddingDuplicatePhoneNumber() {
        assertThrows(AlreadyExistsException.class,
                () -> contact.addPhoneNumber(buildPhoneNumber(1L)));
    }

    @Test
    void shouldThrowWhenRemovingNonExistentPhoneNumber() {
        assertThrows(NotFoundException.class,
                () -> contact.removePhoneNumber(99L));
    }

}