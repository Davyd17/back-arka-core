package com.arka.adapters;

import com.arka.entities.information.Contact;
import com.arka.information.address.AddressEntityMapperImpl;
import com.arka.information.contact.ContactEntityMapperImpl;
import com.arka.information.contact.ContactRepository;
import com.arka.information.contact.ContactServiceAdapter;
import com.arka.information.phonenumber.PhoneNumberEntityMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ContactServiceAdapter.class,
        ContactEntityMapperImpl.class,
        AddressEntityMapperImpl.class,
        PhoneNumberEntityMapperImpl.class})
class ContactServiceAdapterTest {

    @Autowired
    private ContactServiceAdapter contactAdapter;

    @Autowired
    private ContactRepository repository;

    @Test
    void shouldSaveAndPersistNewContactSuccessfully() {
        // given
        Contact newContact = Contact.create(
                "John",
                "Doe",
                "john.doe@arka.com"
        );
        newContact.setCompanyPosition("Lead Software Engineer");

        // when
        Contact savedContact = contactAdapter.save(newContact);

        // then
        assertThat(savedContact).isNotNull();
        assertThat(savedContact.getId()).isNotNull();
        assertThat(savedContact.getName()).isEqualTo("John");
        assertThat(savedContact.isActive()).isTrue();
        assertThat(savedContact.getAddresses()).isEmpty();
        assertThat(savedContact.getPhoneNumbers()).isEmpty();

        // Verify entity directly in database
        assertThat(repository.findById(savedContact.getId())).isPresent();
    }

    @Test
    void shouldUpdateExistingContactSuccessfully() {
        // given: persist initial contact
        Contact initialContact = Contact.create(
                "Jane",
                "Smith",
                "jane.smith@arka.com"
        );

        Contact persistedContact = contactAdapter.save(initialContact);

        // when: deactivate domain object and save updates
        persistedContact.deactivate();
        persistedContact.setCompanyPosition("CTO");

        Long repositoryCountBeforeUpdate = repository.count();
        Contact updatedContact = contactAdapter.save(persistedContact);

        // then
        assertThat(updatedContact.getId()).isEqualTo(persistedContact.getId());
        assertThat(updatedContact.isActive()).isFalse();
        assertThat(updatedContact.getCompanyPosition()).isEqualTo("CTO");

        // Verify count remains the same before updated in database
        assertThat(repository.count()).isEqualTo(repositoryCountBeforeUpdate);
    }
}
