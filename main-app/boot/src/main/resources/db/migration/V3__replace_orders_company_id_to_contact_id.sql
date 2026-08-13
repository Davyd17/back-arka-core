ALTER TABLE orders
    DROP CONSTRAINT fk_orders_company;

ALTER TABLE orders
    RENAME COLUMN company_id TO contact_id;

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_contact
        FOREIGN KEY (contact_id)
            REFERENCES contacts (id);

