ALTER TABLE shopping_carts
    RENAME COLUMN user_id TO contact_id;

ALTER TABLE shopping_carts
    ADD CONSTRAINT fk_shopping_carts_contact
        FOREIGN KEY (contact_id)
            REFERENCES contacts(id);

DROP INDEX idx_carts_user_id;

CREATE INDEX idx_carts_contact_id ON shopping_carts(contact_id);
