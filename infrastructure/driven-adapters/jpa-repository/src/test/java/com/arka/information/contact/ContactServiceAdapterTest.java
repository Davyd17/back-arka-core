package com.arka.information.contact;

import com.arka.company.CompanyEntity;
import com.arka.company.CompanyEntityMapper;
import com.arka.company.CompanyEntityMapperImpl;
import com.arka.entities.Company;
import com.arka.entities.information.Contact;
import com.arka.factory.CompanyTestDataFactory;
import com.arka.information.address.AddressEntityMapperImpl;
import com.arka.information.phonenumber.PhoneNumberEntityMapperImpl;
import com.arka.product.category.ProductCategoryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ContactServiceAdapter.class,
        ContactEntityMapperImpl.class,
        AddressEntityMapperImpl.class,
        PhoneNumberEntityMapperImpl.class,
        CompanyEntityMapperImpl.class,
        ProductCategoryMapperImpl.class})
class ContactServiceAdapterTest {
    @Autowired
    private ContactServiceAdapter contactAdapter;

    @Autowired
    private ContactRepository repository;

    @Autowired
    private CompanyEntityMapper companyEntityMapper;

    @Autowired
    private TestEntityManager entityManager;

    private CompanyTestDataFactory companyTestDataFactory;

    @BeforeEach
    void setUp() {
        companyTestDataFactory = new CompanyTestDataFactory(entityManager);
    }

    @Test
    void shouldSaveAndPersistNewContactSuccessfully() {
        // given
        Contact newContact = Contact.create(
                "John",
                "Doe",
                "john@arka.com"
        );

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
        // given: persist initial company via factory
        CompanyEntity companyEntity = companyTestDataFactory.createCompany("TestCompany");
        Company company = companyEntityMapper.toDomain(companyEntity);

        // persist initial contact
        Contact initialContact = Contact.create(
                "Jane",
                "Smith",
                "jane.smith@arka.com"
        );

        Contact persistedContact = contactAdapter.save(initialContact);

        // when: deactivate domain object and save updates
        persistedContact.deactivate();
        persistedContact.assignCompany(company, "CTO");

        Long repositoryCountBeforeUpdate = repository.count();
        Contact updatedContact = contactAdapter.save(persistedContact);

        // then
        assertThat(updatedContact.getId()).isEqualTo(persistedContact.getId());
        assertThat(updatedContact.isActive()).isFalse();
        assertThat(updatedContact.getCompanyPosition()).isEqualTo("CTO");

        // Verify count remains the same before/after update in database
        assertThat(repository.count()).isEqualTo(repositoryCountBeforeUpdate);
    }}
