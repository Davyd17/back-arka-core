-- =========================
-- COMPANIES
-- =========================
INSERT INTO companies (name, relation)
VALUES ('Arka S.A.S', 'OWN'),
       ('TechDistrib Colombia', 'SUPPLIER'),
       ('ElectroMayorista Ltda', 'SUPPLIER'),
       ('Almacenes TechPro', 'CUSTOMER'),
       ('Distribuidora Digital Norte', 'CUSTOMER'),
       ('Gadgets Express S.A.S', 'CUSTOMER');

-- =========================
-- CONTACTS
-- =========================
INSERT INTO contacts (name, last_name, company_position, email, is_active, company_id)
VALUES
    -- Arka
    ('Carlos', 'Mendoza', 'CEO', 'carlos.mendoza@arka.com', true, 1),
    -- TechDistrib
    ('Andrés', 'Ramírez', 'Gerente Comercial', 'andres.ramirez@techdistrib.com', true, 2),
    ('Laura', 'Gómez', 'Ejecutiva de Ventas', 'laura.gomez@techdistrib.com', true, 2),
    -- ElectroMayorista
    ('Jorge', 'Herrera', 'Director Comercial', 'jorge.herrera@electromayorista.com', true, 3),
    -- Almacenes TechPro
    ('María', 'Castro', 'Gerente de Compras', 'maria.castro@techpro.com', true, 4),
    -- Distribuidora Digital Norte
    ('Felipe', 'Vargas', 'Jefe de Compras', 'felipe.vargas@digitalnorte.com', true, 5),
    -- Gadgets Express
    ('Valentina', 'Torres', 'Directora Comercial', 'valentina.torres@gadgetsexpress.com', true, 6);

-- =========================
-- ADDRESSES
-- =========================
INSERT INTO addresses (country, city, zip_code, address, type, is_active, contact_id)
VALUES ('Colombia', 'Medellín', '050001', 'Calle 10 # 43E-31, El Poblado', 'EMPLOYEE', true, 1),
       ('Colombia', 'Bogotá', '110111', 'Carrera 7 # 71-21, Chapinero', 'SUPPLIER', true, 2),
       ('Colombia', 'Bogotá', '110111', 'Carrera 7 # 71-21, Chapinero', 'SUPPLIER', true, 3),
       ('Colombia', 'Cali', '760001', 'Avenida 6N # 23-45, Granada', 'SUPPLIER', true, 4),
       ('Colombia', 'Medellín', '050021', 'Carrera 43A # 34-95, Laureles', 'CUSTOMER', true, 5),
       ('Colombia', 'Barranquilla', '080001', 'Calle 72 # 57-43, El Prado', 'CUSTOMER', true, 6),
       ('Colombia', 'Medellín', '050040', 'Calle 30 # 65-50, Conquistadores', 'CUSTOMER', true, 7),
       ('Colombia', 'Medellín', '050001', 'Carrera 50 # 10-20, Guayabal', 'WAREHOUSE', true, 1),
       ('Colombia', 'Bogotá', '110221', 'Avenida 68 # 22-30, Fontibón', 'WAREHOUSE', true, 1);

-- =========================
-- PHONE NUMBERS
-- =========================
INSERT INTO phone_numbers (country_code, extension, phone, is_active, contact_id)
VALUES ('+57', null, '3001234567', true, 1),
       ('+57', '101', '6014567890', true, 2),
       ('+57', null, '3109876543', true, 3),
       ('+57', '201', '6023456789', true, 4),
       ('+57', null, '3157654321', true, 5),
       ('+57', null, '3143216547', true, 6),
       ('+57', null, '3168765432', true, 7);

-- =========================
-- EMPLOYEES
-- =========================
INSERT INTO employees (code, contact_id)
VALUES (1001, 1);

-- =========================
-- PRODUCT CATEGORIES
-- =========================
INSERT INTO product_categories (name, slug)
VALUES ('Computadores y Portátiles', 'computadores-y-portatiles'),
       ('Smartphones y Tablets', 'smartphones-y-tablets'),
       ('Accesorios de Cómputo', 'accesorios-de-computo'),
       ('Redes y Conectividad', 'redes-y-conectividad'),
       ('Almacenamiento', 'almacenamiento'),
       ('Audio y Video', 'audio-y-video');

-- =========================
-- COMPANIES - PRODUCT CATEGORIES
-- =========================
INSERT INTO companies_product_categories (company_id, product_category_id)
VALUES
    -- TechDistrib supplies all categories
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    -- ElectroMayorista supplies networking and storage
    (3, 4),
    (3, 5),
    -- Almacenes TechPro buys computers and smartphones
    (4, 1),
    (4, 2),
    -- Distribuidora Digital Norte buys accessories and networking
    (5, 3),
    (5, 4),
    -- Gadgets Express buys everything
    (6, 1),
    (6, 2),
    (6, 3),
    (6, 4),
    (6, 5),
    (6, 6);

-- =========================
-- PRODUCTS
-- =========================
INSERT INTO products (sku, name, description, attributes, base_price, is_active, category_id)
VALUES ('COMP-001', 'Lenovo ThinkPad E14 Gen 5',
        'Portátil empresarial con procesador Intel Core i5, 16GB RAM, 512GB SSD',
        '{"processor": "Intel Core i5-1335U", "ram": "16GB", "storage": "512GB SSD", "display": "14 pulgadas FHD"}',
        3850000.00, true, 1),

       ('COMP-002', 'HP ProBook 450 G10',
        'Portátil profesional con procesador Intel Core i7, 16GB RAM, 1TB SSD',
        '{"processor": "Intel Core i7-1355U", "ram": "16GB", "storage": "1TB SSD", "display": "15.6 pulgadas FHD"}',
        4950000.00, true, 1),

       ('COMP-003', 'Dell OptiPlex 7010',
        'Desktop empresarial compacto con Intel Core i5, 8GB RAM, 256GB SSD',
        '{"processor": "Intel Core i5-13500T", "ram": "8GB", "storage": "256GB SSD", "form_factor": "Micro"}',
        2800000.00, true, 1),

       ('SMRT-001', 'Samsung Galaxy A54 5G',
        'Smartphone empresarial con pantalla AMOLED 6.4 pulgadas, 128GB',
        '{"display": "6.4 pulgadas AMOLED", "storage": "128GB", "ram": "6GB", "network": "5G"}',
        1250000.00, true, 2),

       ('SMRT-002', 'iPhone 15',
        'Smartphone Apple con chip A16, 128GB, cámara 48MP',
        '{"chip": "A16 Bionic", "storage": "128GB", "camera": "48MP", "display": "6.1 pulgadas"}',
        4200000.00, true, 2),

       ('TABL-001', 'Samsung Galaxy Tab A9+',
        'Tablet empresarial 11 pulgadas, 128GB, WiFi + 5G',
        '{"display": "11 pulgadas TFT", "storage": "128GB", "ram": "8GB", "network": "WiFi + 5G"}',
        1100000.00, true, 2),

       ('ACCS-001', 'Logitech MX Keys S',
        'Teclado inalámbrico empresarial con retroiluminación inteligente',
        '{"connection": "Bluetooth + USB", "backlight": true, "layout": "Español"}',
        420000.00, true, 3),

       ('ACCS-002', 'Logitech MX Master 3S',
        'Mouse inalámbrico de alta precisión para profesionales',
        '{"connection": "Bluetooth + USB", "dpi": "8000", "buttons": 7}',
        380000.00, true, 3),

       ('ACCS-003', 'Monitor LG 27UK850',
        'Monitor 4K UHD 27 pulgadas con USB-C',
        '{"resolution": "3840x2160", "panel": "IPS", "ports": "HDMI, DP, USB-C", "size": "27 pulgadas"}',
        1850000.00, true, 3),

       ('NET-001', 'Cisco Switch 24 puertos',
        'Switch empresarial gestionable 24 puertos Gigabit',
        '{"ports": 24, "speed": "1Gbps", "managed": true, "poe": false}',
        1500000.00, true, 4),

       ('NET-002', 'Ubiquiti UniFi AP Pro',
        'Access Point WiFi 6 empresarial con 4x4 MIMO',
        '{"wifi_standard": "WiFi 6", "mimo": "4x4", "coverage": "150m2", "poe": true}',
        1220000.00, true, 4),

       ('STR-001', 'Western Digital 1TB SSD NVMe',
        'Disco sólido NVMe M.2 1TB para laptops empresariales',
        '{"capacity": "1TB", "interface": "NVMe M.2", "read_speed": "3500MB/s", "write_speed": "3000MB/s"}',
        450000.00, true, 5),

       ('STR-002', 'Seagate NAS 4TB',
        'Disco duro para NAS empresarial 4TB 7200RPM',
        '{"capacity": "4TB", "rpm": 7200, "interface": "SATA", "use": "NAS"}',
        650000.00, true, 5),

       ('AUD-001', 'Jabra Evolve2 55',
        'Auriculares inalámbricos con cancelación de ruido para empresas',
        '{"noise_cancelling": true, "connection": "Bluetooth + USB", "battery": "36h", "microphone": "8 mics"}',
        1150000.00, true, 6);

-- =========================
-- WAREHOUSES
-- =========================
INSERT INTO warehouses (is_active, address_id)
VALUES (true, 8), -- Medellín warehouse
       (true, 9);
-- Bogotá warehouse

-- =========================
-- WAREHOUSE INVENTORY
-- =========================
INSERT INTO warehouses_inventory (stock, warehouse_id, product_id)
VALUES
    -- Medellín warehouse
    (15, 1, 1),  -- ThinkPad E14
    (10, 1, 2),  -- HP ProBook
    (20, 1, 3),  -- Dell OptiPlex
    (50, 1, 4),  -- Samsung A54
    (25, 1, 5),  -- iPhone 15
    (30, 1, 6),  -- Galaxy Tab
    (100, 1, 7), -- MX Keys
    (100, 1, 8), -- MX Master
    (20, 1, 9),  -- LG Monitor
    (8, 1, 10),  -- Cisco Switch
    (35, 1, 11), -- UniFi AP
    (80, 1, 12), -- WD SSD
    (60, 1, 13), -- Seagate NAS
    (45, 1, 14), -- Jabra
    -- Bogotá warehouse
    (10, 2, 1),
    (8, 2, 2),
    (15, 2, 3),
    (40, 2, 4),
    (20, 2, 5),
    (25, 2, 7),
    (25, 2, 8),
    (60, 2, 12),
    (40, 2, 13);

-- =========================
-- INVENTORY MOVEMENTS
-- =========================
INSERT INTO inventory_movements (type, quantity, previous_stock, new_stock, notes, product_id, employee_id,
                                 warehouse_inventory_id)
VALUES ('IN', 15, 0, 15, 'Compra inicial ThinkPad E14 - Medellín', 1, 1, 1),
       ('IN', 10, 0, 10, 'Compra inicial HP ProBook - Medellín', 2, 1, 2),
       ('IN', 20, 0, 20, 'Compra inicial Dell OptiPlex - Medellín', 3, 1, 3),
       ('IN', 50, 0, 50, 'Compra inicial Samsung A54 - Medellín', 4, 1, 4),
       ('IN', 25, 0, 25, 'Compra inicial iPhone 15 - Medellín', 5, 1, 5),
       ('IN', 10, 0, 10, 'Compra inicial Bogotá ThinkPad', 1, 1, 15);

-- =========================
-- ORDERS
-- =========================
INSERT INTO orders (number, status, total_price, notes, type, contact_id)
VALUES ('ORD-2024-001', 'AUTHORIZED', 23050000.00, 'Pedido corporativo Q1 2024', 'SALES', 5), -- María Castro (TechPro)
       ('ORD-2024-002', 'PENDING', 8460000.00, 'Reposición de accesorios', 'SALES', 6),       -- Felipe Vargas (Digital Norte)
       ('ORD-2024-003', 'PROCESSING', 15750000.00, 'Equipos para nueva sede', 'SALES',
        7),                                                                                   -- Valentina Torres (Gadgets Express)
       ('ORD-2024-004', 'CANCELLED', 4200000.00, 'Cancelado por cliente', 'SALES', 5),        -- María Castro (TechPro)
       ('PUR-2024-001', 'AUTHORIZED', 38500000.00, 'Compra a proveedor TechDistrib', 'PURCHASE', 2);
-- Andrés Ramírez (TechDistrib)

-- =========================
-- ORDER ITEMS
-- =========================
INSERT INTO order_items (order_id, product_id, quantity, unit_price_snapshot, total_price)
VALUES
    -- ORD-2024-001 Almacenes TechPro
    (1, 1, 3, 3850000.00, 11550000.00), -- 3x ThinkPad
    (1, 5, 2, 4200000.00, 8400000.00),  -- 2x iPhone 15
    (1, 7, 5, 420000.00, 2100000.00),   -- 5x MX Keys
    (1, 8, 5, 380000.00, 1900000.00),   -- 5x MX Master
    -- ORD-2024-002 Distribuidora Digital Norte
    (2, 7, 10, 420000.00, 4200000.00),  -- 10x MX Keys
    (2, 8, 8, 380000.00, 3040000.00),   -- 8x MX Master
    (2, 11, 1, 1220000.00, 1220000.00), -- 1x UniFi AP
    -- ORD-2024-003 Gadgets Express
    (3, 2, 2, 4950000.00, 9900000.00),  -- 2x HP ProBook
    (3, 4, 3, 1250000.00, 3750000.00),  -- 3x Samsung A54
    (3, 14, 1, 1150000.00, 1150000.00), -- 1x Jabra
    -- ORD-2024-004 Cancelled
    (4, 5, 1, 4200000.00, 4200000.00),  -- 1x iPhone 15
    -- PUR-2024-001 Purchase from TechDistrib
    (5, 1, 10, 3850000.00, 38500000.00);
-- 10x ThinkPad

-- =========================
-- SHIPPING DETAILS
-- =========================
INSERT INTO shipping_details (carrier, tracking_number, notes, status, order_id, origin_address_id,
                              destination_address_id)
VALUES ('Servientrega', 'SRV-2024-001234', 'Entrega en sede principal', 'DELIVERED', 1, 8, 5),
       ('Coordinadora', 'COO-2024-005678', 'Entrega parcial primer envío', 'IN_DISPATCH', 3, 8, 7);

-- =========================
-- SHOPPING CARTS
-- =========================
INSERT INTO shopping_carts (status, total_amount, contact_id)
VALUES
    -- Active cart for Carlos containing some accessories
    ('ACTIVE', 1530000.00, 1),
    -- Past processed cart that correlates to an ordered set of items
    ('PROCESSED', 15750000.00, 1),
    -- An abandoned cart from a previous session
    ('ABANDONED', 4200000.00, 1);

-- =========================
-- SHOPPING CART ITEMS
-- =========================
INSERT INTO shopping_cart_items (quantity, unit_price, sub_total, shopping_cart_id, product_id)
VALUES
    -- Items for Cart 1 (ACTIVE)
    (1, 1150000.00, 1150000.00, 1, 14), -- 1x Jabra Evolve2 55
    (1, 380000.00, 380000.00, 1, 8),    -- 1x Logitech MX Master 3S

    -- Items for Cart 2 (PROCESSED) - Matches ORD-2024-003 quantities/prices
    (2, 4950000.00, 9900000.00, 2, 2),  -- 2x HP ProBook 450
    (3, 1250000.00, 3750000.00, 2, 4),  -- 3x Samsung Galaxy A54
    (1, 1150000.00, 1150000.00, 2, 14), -- 1x Jabra Evolve2 55

    -- Items for Cart 3 (ABANDONED) - Matches ORD-2024-004
    (1, 4200000.00, 4200000.00, 3, 5); -- 1x iPhone 15
