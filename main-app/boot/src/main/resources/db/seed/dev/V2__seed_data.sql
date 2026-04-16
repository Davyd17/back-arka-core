-- =========================
-- COMPANIES
-- =========================
INSERT INTO companies (name, relation) VALUES
    ('Arka Tech SAS', 'OWN'),
    ('Lenovo Colombia SAS', 'SUPPLIER'),
    ('Ingram Micro Colombia', 'SUPPLIER'),
    ('Soluciones TI Medellín SAS', 'CUSTOMER'),
    ('Servicios Digitales Bogotá SAS', 'CUSTOMER');

-- =========================
-- CONTACTS
-- =========================
INSERT INTO contacts (name, last_name, "position", email, company_id) VALUES
    ('Carlos', 'Ramírez', 'Compras', 'compras@solucionesti.com', 4),
    ('Andrea', 'López', 'Gerente TI', 'ti@serviciosdigitales.com', 5),
    ('Sofía', 'Martínez', 'Ventas', 'ventas@lenovo.com', 2),
    ('Luis', 'Fernández', 'Account Manager', 'luis@ingrammicro.com', 3);

-- =========================
-- ADDRESSES
-- =========================
INSERT INTO addresses (country, city, address, type, contact_id) VALUES
    ('Colombia', 'Medellín', 'Calle 10 #45-20', 'CUSTOMER', 1),
    ('Colombia', 'Bogotá', 'Carrera 15 #100-50', 'CUSTOMER', 2),
    ('Colombia', 'Bogotá', 'Zona Franca Ingram', 'SUPPLIER', 4),
    ('Colombia', 'Medellín', 'Bodega Arka Central', 'WAREHOUSE', 1);

-- =========================
-- PRODUCT CATEGORIES
-- =========================
INSERT INTO product_categories (name, slug) VALUES
    ('Laptops', 'laptops'),
    ('Monitores', 'monitores'),
    ('Accesorios', 'accesorios');

-- =========================
-- PRODUCTS
-- =========================
INSERT INTO products (sku, name, description, base_price, category_id) VALUES
    ('LEN-E14', 'Lenovo ThinkPad E14', 'Laptop empresarial 14"', 3200000, 1),
    ('LEN-T14', 'Lenovo ThinkPad T14', 'Laptop alto rendimiento', 5200000, 1),
    ('LG-24IPS', 'Monitor LG 24 IPS', 'Monitor 24 pulgadas IPS', 650000, 2),
    ('LOG-M185', 'Mouse Logitech M185', 'Mouse inalámbrico', 35000, 3),
    ('LOG-K120', 'Teclado Logitech K120', 'Teclado USB', 45000, 3);

-- =========================
-- COMPANIES - PRODUCT CATEGORIES
-- =========================
INSERT INTO companies_product_categories (company_id, product_category_id) VALUES
    (2, 1),
    (3, 1),
    (3, 2),
    (3, 3);

-- =========================
-- EMPLOYEES
-- =========================
INSERT INTO employees (code, contact_id) VALUES
    (1001, 1);

-- =========================
-- WAREHOUSES
-- =========================
INSERT INTO warehouses (address_id) VALUES
    (4);

-- =========================
-- WAREHOUSE INVENTORY
-- =========================
INSERT INTO warehouses_inventory (warehouse_id, product_id, stock) VALUES
    (1, 1, 10),
    (1, 2, 5),
    (1, 3, 20),
    (1, 4, 100),
    (1, 5, 80);

-- =========================
-- INVENTORY MOVEMENTS
-- =========================
INSERT INTO inventory_movements (
    type, quantity, previous_stock, new_stock, product_id, employee_id, warehouse_id
) VALUES
      ('IN', 10, 0, 10, 1, 1, 1),
      ('IN', 5, 0, 5, 2, 1, 1),
      ('IN', 20, 0, 20, 3, 1, 1);

-- =========================
-- ORDERS
-- =========================
INSERT INTO orders (number, type, company_id, total_price) VALUES
    ('ORD-001', 'PURCHASE', 4, 3270000),
    ('ORD-002', 'PURCHASE', 5, 650000);

-- =========================
-- ORDER ITEMS
-- =========================
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
    (1, 1, 1, 3200000),
    (1, 4, 2, 35000),
    (2, 3, 1, 650000);

-- =========================
-- SHIPPING DETAILS
-- =========================
INSERT INTO shipping_details (
    carrier, tracking_number, order_id, origin_address_id, destination_address_id
) VALUES
      ('Servientrega', 'TRK123456', 1, 4, 1),
      ('Coordinadora', 'TRK654321', 2, 4, 2);

-- =========================
-- SHOPPING CARTS
-- =========================
INSERT INTO shopping_carts (user_id, total_amount) VALUES
    (1, 70000);

-- =========================
-- SHOPPING CART ITEMS
-- =========================
INSERT INTO shopping_cart_items (shopping_cart_id, product_id, quantity, unit_price) VALUES
    (1, 4, 2, 35000);