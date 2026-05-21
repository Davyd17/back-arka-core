-- =========================
-- COMPANIES
-- =========================
CREATE TABLE companies (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    relation VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT companies_relation_check CHECK (
        relation IN ('SUPPLIER', 'CUSTOMER', 'OWN')
        )
);

-- =========================
-- CONTACTS
-- =========================
CREATE TABLE contacts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    "position" VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    user_id BIGINT UNIQUE,
    company_id BIGINT NOT NULL,
    CONSTRAINT fk_contacts_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE INDEX idx_contacts_company_id ON contacts(company_id);
CREATE INDEX idx_contacts_email ON contacts(email);

-- =========================
-- ADDRESSES
-- =========================
CREATE TABLE addresses (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    zip_code VARCHAR(50),
    address VARCHAR(200) NOT NULL,
    notes TEXT,
    type VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    contact_id BIGINT NOT NULL,
    CONSTRAINT fk_addresses_contact FOREIGN KEY (contact_id) REFERENCES contacts(id),
    CONSTRAINT addresses_type_check CHECK (
        type IN ('WAREHOUSE', 'SUPPLIER', 'EMPLOYEE', 'CUSTOMER')
        )
);

CREATE INDEX idx_addresses_contact_id ON addresses(contact_id);
CREATE INDEX idx_addresses_type ON addresses(type);

-- =========================
-- PHONE NUMBERS
-- =========================
CREATE TABLE phone_numbers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country_code VARCHAR(10) NOT NULL,
    extension VARCHAR(10),
    phone VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    contact_id BIGINT NOT NULL,
    CONSTRAINT fk_phone_numbers_contact FOREIGN KEY (contact_id) REFERENCES contacts(id)
);

CREATE INDEX idx_phone_numbers_contact_id ON phone_numbers(contact_id);

-- =========================
-- EMPLOYEES
-- =========================
CREATE TABLE employees (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    contact_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_employees_contact FOREIGN KEY (contact_id) REFERENCES contacts(id)
);

CREATE INDEX idx_employees_contact_id ON employees(contact_id);
CREATE UNIQUE INDEX idx_employees_code ON employees(code);

-- =========================
-- PRODUCT CATEGORIES
-- =========================
CREATE TABLE product_categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT product_categories_slug_check CHECK (
        slug = LOWER(slug)
        )
);

CREATE UNIQUE INDEX idx_product_categories_slug ON product_categories(slug);

-- =========================
-- PRODUCTS
-- =========================
CREATE TABLE products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sku VARCHAR(300) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    attributes JSONB,
    base_price NUMERIC(12,2) DEFAULT 0 NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES product_categories(id),
    CONSTRAINT products_base_price_check CHECK (
        base_price >= 0
        )
);

CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_name ON products(name);
CREATE UNIQUE INDEX idx_products_sku ON products(sku);

-- =========================
-- COMPANIES - PRODUCT CATEGORIES
-- =========================
CREATE TABLE companies_product_categories (
    company_id BIGINT NOT NULL,
    product_category_id BIGINT NOT NULL,
    CONSTRAINT fk_cpc_company FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT fk_cpc_category FOREIGN KEY (product_category_id) REFERENCES product_categories(id)
);

CREATE INDEX idx_cpc_company_id ON companies_product_categories(company_id);
CREATE INDEX idx_cpc_category_id ON companies_product_categories(product_category_id);

-- =========================
-- ORDERS
-- =========================
CREATE TABLE orders (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number VARCHAR(100) NOT NULL UNIQUE,
    status VARCHAR(100) DEFAULT 'PENDING' NOT NULL,
    total_price NUMERIC(12,2) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    notes TEXT,
    type VARCHAR(100) NOT NULL,
    company_id BIGINT NOT NULL,
    CONSTRAINT fk_orders_company FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT orders_status_check CHECK (
        status IN ('AUTHORIZED', 'CANCELLED', 'PROCESSING', 'PENDING')
        ),
    CONSTRAINT orders_total_price_check CHECK (
        total_price >= 0
        )
);

CREATE UNIQUE INDEX idx_orders_number ON orders(number);
CREATE INDEX idx_orders_company_id ON orders(company_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at);

-- =========================
-- ORDER ITEMS
-- =========================
CREATE TABLE order_items (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price_snapshot NUMERIC(12,2) DEFAULT 0 NOT NULL,
    total_price NUMERIC(12,2) DEFAULT 0 NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT order_items_quantity_check CHECK (quantity > 0),
    CONSTRAINT order_items_unit_price_snapshot_check CHECK (unit_price_snapshot >= 0),
    CONSTRAINT order_items_total_price_check CHECK (total_price >= 0)
);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);

-- =========================
-- SHIPPING DETAILS
-- =========================
CREATE TABLE shipping_details (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    carrier VARCHAR(100) NOT NULL,
    tracking_number VARCHAR(200) NOT NULL UNIQUE,
    notes TEXT,
    status VARCHAR(100) DEFAULT 'PENDING' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    order_id BIGINT NOT NULL,
    origin_address_id BIGINT NOT NULL,
    destination_address_id BIGINT NOT NULL,
    CONSTRAINT fk_shipping_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_shipping_origin FOREIGN KEY (origin_address_id) REFERENCES addresses(id),
    CONSTRAINT fk_shipping_destination FOREIGN KEY (destination_address_id) REFERENCES addresses(id),
    CONSTRAINT shipping_details_status_check CHECK (
        status IN ('SENT', 'RECEIVED', 'PENDING', 'DELIVERED', 'IN_DISPATCH')
        )
);

CREATE INDEX idx_shipping_order_id ON shipping_details(order_id);
CREATE INDEX idx_shipping_status ON shipping_details(status);

-- =========================
-- SHOPPING CARTS
-- =========================
CREATE TABLE shopping_carts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL,
    total_amount NUMERIC(12,2) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT shopping_carts_status_check CHECK (
        status IN ('ACTIVE', 'PROCESSED', 'CANCELLED', 'ABANDONED')
        ),
    CONSTRAINT shopping_carts_total_amount_check CHECK (
        total_amount >= 0
        )
);

CREATE INDEX idx_carts_user_id ON shopping_carts(user_id);
CREATE INDEX idx_carts_status ON shopping_carts(status);

-- =========================
-- SHOPPING CART ITEMS
-- =========================
CREATE TABLE shopping_cart_items (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(12,2) DEFAULT 0 NOT NULL,
    subtotal NUMERIC(12,2) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    shopping_cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_cart_items_cart FOREIGN KEY (shopping_cart_id) REFERENCES shopping_carts(id),
    CONSTRAINT fk_cart_items_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT shopping_cart_items_quantity_check CHECK (quantity > 0),
    CONSTRAINT shopping_cart_items_unit_price_check CHECK (unit_price >= 0)
);

CREATE INDEX idx_cart_items_cart_id ON shopping_cart_items(shopping_cart_id);
CREATE INDEX idx_cart_items_product_id ON shopping_cart_items(product_id);

-- =========================
-- WAREHOUSES
-- =========================
CREATE TABLE warehouses (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    address_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_warehouses_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);

CREATE INDEX idx_warehouses_address_id ON warehouses(address_id);

-- =========================
-- WAREHOUSE INVENTORY
-- =========================
CREATE TABLE warehouses_inventory (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    warehouse_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_inventory_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id),
    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT warehouses_inventory_stock_check CHECK (stock >= 0)
);

CREATE INDEX idx_inventory_warehouse_id ON warehouses_inventory(warehouse_id);
CREATE INDEX idx_inventory_product_id ON warehouses_inventory(product_id);

CREATE UNIQUE INDEX idx_inventory_unique
    ON warehouses_inventory(warehouse_id, product_id);

-- =========================
-- INVENTORY MOVEMENTS
-- =========================
CREATE TABLE inventory_movements (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    quantity INTEGER NOT NULL,
    previous_stock INTEGER NOT NULL,
    new_stock INTEGER NOT NULL,
    notes TEXT,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    product_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    warehouse_inventory_id BIGINT NOT NULL,
    CONSTRAINT fk_movements_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_movements_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_movements_warehouse_inventory FOREIGN KEY (warehouse_inventory_id) REFERENCES warehouses_inventory(id),
    CONSTRAINT inventory_movements_type_check CHECK (
        type IN ('IN', 'OUT')
        ),
    CONSTRAINT inventory_movements_quantity_check CHECK (quantity > 0),
    CONSTRAINT inventory_movements_previous_stock_check CHECK (previous_stock >= 0),
    CONSTRAINT inventory_movements_new_stock_check CHECK (new_stock >= 0)
);

CREATE INDEX idx_movements_product_id ON inventory_movements(product_id);
CREATE INDEX idx_movements_employee_id ON inventory_movements(employee_id);
CREATE INDEX idx_movements_warehouse_id ON inventory_movements(warehouse_inventory_id);
CREATE INDEX idx_movements_type ON inventory_movements(type);
CREATE INDEX idx_movements_registered_at ON inventory_movements(registered_at);