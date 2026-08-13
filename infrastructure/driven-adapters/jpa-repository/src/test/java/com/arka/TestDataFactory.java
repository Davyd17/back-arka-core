package com.arka;

import com.arka.cart.ShoppingCartEntity;
import com.arka.company.CompanyEntity;
import com.arka.employee.EmployeeEntity;
import com.arka.enums.*;
import com.arka.information.address.AddressEntity;
import com.arka.information.contact.ContactEntity;
import com.arka.information.phonenumber.PhoneNumberEntity;
import com.arka.inventory.warehouse.WarehouseInventoryEntity;
import com.arka.order.OrderEntity;
import com.arka.product.ProductEntity;
import com.arka.product.category.ProductCategoryEntity;
import com.arka.warehouse.WarehouseEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class TestDataFactory {

    private final TestEntityManager entityManager;

    public TestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CompanyEntity createCompany() {
        CompanyEntity company = CompanyEntity.builder()
                .name("Arka Corp")
                .relation(CompanyRelationType.OWN)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(company);
    }

    public CompanyEntity createCompany(String name) {
        CompanyEntity company = CompanyEntity.builder()
                .name(name)
                .relation(CompanyRelationType.CUSTOMER)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(company);
    }

    public ContactEntity createContact() {
        CompanyEntity company = createCompany();

        ContactEntity contact = ContactEntity.builder()
                .name("John")
                .lastName("Doe")
                .companyPosition("Warehouse Manager")
                .email("john.doe." + System.currentTimeMillis() + "@arka.com")
                .active(true)
                .company(company)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(contact);
    }

    public ContactEntity createContact(CompanyEntity company) {
        ContactEntity contact = ContactEntity.builder()
                .name("Jane")
                .lastName("Smith")
                .companyPosition("Purchasing Manager")
                .email("jane.smith." + System.currentTimeMillis() + "@arka.com")
                .active(true)
                .company(company)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(contact);
    }

    public EmployeeEntity createEmployee() {
        ContactEntity contact = createContact();

        EmployeeEntity employee = EmployeeEntity.builder()
                .code((int) (System.currentTimeMillis() % 100000))
                .contact(contact)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(employee);
    }

    public AddressEntity createAddress() {
        AddressEntity address = AddressEntity.builder()
                .country("Colombia")
                .city("Medellin")
                .zipCode("050001")
                .address("Calle 10 #40-20")
                .type(AddressType.WAREHOUSE)
                .build();

        return entityManager.persistAndFlush(address);
    }

    public WarehouseEntity createWarehouse() {

        AddressEntity address = createAddress();

        WarehouseEntity warehouse = WarehouseEntity.builder()
                .address(address)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(warehouse);
    }

    public PhoneNumberEntity createPhoneNumber() {
        PhoneNumberEntity phoneNumber = PhoneNumberEntity.builder()
                .countryCode("+57")
                .phone("3001234567")
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(phoneNumber);
    }

    public ProductCategoryEntity createProductCategory() {
        ProductCategoryEntity category = ProductCategoryEntity.builder()
                .name("Electronics")
                .slug("electronics-" + System.currentTimeMillis())
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(category);
    }

    public ProductEntity createProduct() {
        ProductCategoryEntity category = createProductCategory();

        ProductEntity product = ProductEntity.builder()
                .sku("PROD-" + System.currentTimeMillis())
                .name("Test Product " + System.currentTimeMillis())
                .description("Standard Test Product Description")
                .basePrice(new BigDecimal("99.99"))
                .attributes(new HashMap<>())
                .active(true)
                .category(category)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(product);
    }

    public ShoppingCartEntity createShoppingCart(ContactEntity contact) {
        ShoppingCartEntity cart = ShoppingCartEntity.builder()
                .status(ShoppingCartStatus.ACTIVE)
                .totalAmount(BigDecimal.ZERO)
                .contact(contact)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(cart);
    }

    public OrderEntity createOrder(ContactEntity contact, Instant createdAt) {
        OrderEntity order = OrderEntity.builder()
                .number("ORD-" + System.currentTimeMillis())
                .status(OrderStatus.PENDING)
                .type(OrderType.PURCHASE)
                .totalPrice(new BigDecimal("99.99"))
                .contact(contact)
                .createdAt(createdAt)
                .build();

        return entityManager.persistAndFlush(order);
    }

    public WarehouseInventoryEntity createWarehouseInventory(WarehouseEntity warehouse, ProductEntity product, Integer stock) {
        WarehouseInventoryEntity inventory = WarehouseInventoryEntity.builder()
                .warehouse(warehouse)
                .product(product)
                .stock(stock)
                .inventoryMovements(new ArrayList<>())
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(inventory);
    }
}
