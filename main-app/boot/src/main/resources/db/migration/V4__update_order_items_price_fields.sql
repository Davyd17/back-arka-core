ALTER TABLE order_items
    RENAME COLUMN unit_price TO unit_price_snapshot;

ALTER TABLE order_items
    ADD COLUMN total_price NUMERIC(19, 2) NOT NULL DEFAULT 0;

UPDATE order_items
    SET total_price = unit_price_snapshot * quantity;

ALTER TABLE order_items
    ALTER COLUMN total_price DROP DEFAULT;