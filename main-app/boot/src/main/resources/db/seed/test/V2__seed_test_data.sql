-- ========================================================
-- MINIMAL INTEGRATION TEST SEED SCRIPT
-- ========================================================

-- 1. Companies
INSERT INTO companies (name, relation)
VALUES ('Arka Corp', 'OWN');

-- 2. Contacts (Depends on Companies)
INSERT INTO contacts (name, last_name, "position", email, company_id, user_id)
VALUES ('John', 'Doe', 'Warehouse Manager', 'john.doe@arka.com', 1, 1001);

-- 3. Addresses (Depends on Contacts)
INSERT INTO addresses (country, city, zip_code, address, type, contact_id)
VALUES
    ('Colombia', 'Medellin', '050001', 'Calle 10 #40-20', 'WAREHOUSE', 1),
    ('Colombia', 'Bogota', '110011', 'Carrera 7 #100-50', 'CUSTOMER', 1);

-- 4. Phone Numbers (Depends on Contacts)
INSERT INTO phone_numbers (country_code, phone, contact_id)
VALUES ('+57', '3001234567', 1);

-- 5. Employees (Depends on Contacts)
INSERT INTO employees (code, contact_id)
VALUES (101, 1);

-- 6. Product Categories
INSERT INTO product_categories (name, slug)
VALUES ('Electronics', 'electronics');

-- 7. Companies <-> Product Categories (Join Table)
INSERT INTO companies_product_categories (company_id, product_category_id)
VALUES (1, 1);

-- 8. Products (Depends on Product Categories)
INSERT INTO products (sku, name, description, base_price, category_id, attributes)
VALUES ('PROD-001', 'Test Product', 'Standard Test Product Description', 99.99, 1, '{"brand": "Arka"}');

-- 9. Warehouses (Depends on Addresses)
INSERT INTO warehouses (address_id)
VALUES (1);

-- 10. Warehouses Inventory (Depends on Warehouses + Products)
INSERT INTO warehouses_inventory (stock, warehouse_id, product_id)
VALUES (50, 1, 1);

-- 11. Inventory Movements (Depends on Products + Employees + Warehouses Inventory)
INSERT INTO inventory_movements (type, quantity, previous_stock, new_stock, product_id, employee_id, warehouse_inventory_id)
VALUES ('IN', 50, 0, 50, 1, 1, 1);

-- 12. Shopping Carts
INSERT INTO shopping_carts (status, total_amount, user_id)
VALUES ('ACTIVE', 99.99, 1001);

-- 13. Shopping Cart Items (Depends on Shopping Carts + Products)
INSERT INTO shopping_cart_items (quantity, unit_price, sub_total, shopping_cart_id, product_id)
VALUES (1, 99.99, 99.99, 1, 1);

-- 14. Orders (Depends on Companies)
INSERT INTO orders (number, status, total_price, type, company_id)
VALUES ('ORD-2026-001', 'PENDING', 99.99, 'PURCHASE', 1);

-- 15. Order Items (Depends on Orders + Products)
INSERT INTO order_items (order_id, product_id, quantity, unit_price_snapshot, total_price)
VALUES (1, 1, 1, 99.99, 99.99);

-- 16. Shipping Details (Depends on Orders + Addresses origin/destination)
INSERT INTO shipping_details (carrier, tracking_number, status, order_id, origin_address_id, destination_address_id)
VALUES ('DHL', 'TRACK-123456', 'PENDING', 1, 1, 2);