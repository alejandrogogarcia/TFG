-- Insertar usuarios
INSERT INTO users (token, expiry_date, first_name, last_name, dni, encrypted_password, email, birth_date, create_date, language, role, image, is_active) VALUES
    (NULL, NULL, 'Admin', 'User', '12345678A', '$2a$10$TvCJgT2rfBWKsN164MsspuFiGTuchlZBVGWaNWy2wgxKWCi.pwdwG', 'admin@example.com', '1985-05-20', NOW(), 'ESP', 'ADMIN', 'user-1-photo.png', TRUE),
    (NULL, NULL, 'John', 'Doe', '87654321B', '$2a$10$TvCJgT2rfBWKsN164MsspuFiGTuchlZBVGWaNWy2wgxKWCi.pwdwG', 'john.doe@example.com', '1990-10-15', NOW(), 'GAL', 'EMPLOYEE', NULL, TRUE),
    (NULL, NULL, 'Jane', 'Smith', '56781234C', '$2a$10$TvCJgT2rfBWKsN164MsspuFiGTuchlZBVGWaNWy2wgxKWCi.pwdwG', 'jane.smith@example.com', '1995-08-25', NOW(), 'ENG', 'CLERK', NULL, FALSE);

-- Insertar tipos de impuestos
INSERT INTO product_taxes (type, description, value) VALUES
    ('IVA General', 'Electrodomésticos, electricidad, vehículos, smartphones, etc.' , 21.0),
    ('IVA Reducido', 'Actividades culturales, entradas para el cine, medios de transporte o electricidad' , 10.0),
    ('IVA Superreducido', 'Bienes de primera necesidad, libros, periódicos y revistas, artículos de higiene femenina y preservativos, y viviendas de protección oficial' , 4.0);

-- Insertar categorías
INSERT INTO categories (name, description, create_date, creator_id) VALUES
    ('Electrónica', 'Dispositivos y aparatos electrónicos', NOW(), 1),
    ('Hogar', 'Artículos para el hogar y cocina', NOW(), 1),
    ('Libros', 'Libros y literatura', NOW(), 2);

-- Insertar productos
INSERT INTO products (reference, name, description, image, data, price, discount, stock, create_date, tax_type_id, category_id, creator_id) VALUES
    ('REF-001', 'Smartphone', 'Smartphone de última generación', 'Product-1.jpg', 'Data-1.pdf', 300.00, 10, 50, NOW(), 1, 1, 1),
    ('REF-002', 'Batidora', 'Batidora multifunción para cocina', NULL, NULL, 100.00, 0, 30, NOW(), 2, 2, 2),
    ('REF-003', 'Libro Java', 'Manual avanzado de programación Java', NULL, NULL, 50.00, 20, 100, NOW(), 2, 3, 2);

-- Insertar clientes
INSERT INTO clients (first_name, last_name, dni, address, city, post_code, email, phone_number, create_date, creator_id) VALUES
    ('Laura', 'González', '11111111A', 'Calle Real 12', 'Madrid', 28001, 'laura.gonzalez@example.com', 612345678, NOW(), 1),
    ('Carlos', 'Pérez', '22222222B', 'Av. del Mar 45', 'Valencia', 46010, 'carlos.perez@example.com', 678901234, NOW(), 2),
    ('Marta', 'López', '33333333C', 'Rúa Nova 33', 'Santiago', 15782, 'marta.lopez@example.com', 699998877, NOW(), 1);

-- Insertar facturas
INSERT INTO invoices (subtotal, taxes, total, create_date, client_id, creator_id) VALUES
    (650.00, 136.50, 786.50, NOW(), 3, 1),
    (200.00, 20.00, 220.00, NOW(), 2, 2);

-- Insertar notas (albaranes)
INSERT INTO notes (subtotal, taxes, total, comment, create_date, client_id, creator_id, invoice_id) VALUES
    (650.00, 118.40, 768.40, '', NOW(), 3, 2, 1),
    (100.00, 10.00, 110.00, '', NOW(), 2, 2, 2),
    (30.00, 3.00, 33.00, 'Mercancia retirada por Luke', NOW(), 2, 2, null),
    (100.00, 10.00, 110.00, '', NOW(), 2, 1, 2);


-- Insertar líneas de notas
INSERT INTO notelines (note_id, product_id, price, amount, discount, taxes, comment) VALUES
    (1, 1, 300.00, 2, 0, 113.40, 'Nº Serie XXXX'),
    (1, 2, 100.00, 1, 50, 5.00, 'Reacondicionada'),
    (2, 2, 100.00, 1, 0, 10.00, 'Batidora multifunción'),
    (3, 3, 40.00, 1, 25, 3.00, 'Libro de programación Java'),
    (4, 2, 100.00, 1, 0, 10.00, '');

-- Insertar información de la empresa
INSERT INTO company_info (name, address, post_code, nif, email, web) VALUES
    (
           'Empresa Exemplo S.A.',
           'Rúa Exemplo, 123, Santiago de Compostela, Galicia',
           15705,
           'X12345678',
           'info@empresaexemplo.com',
           'www.empresaexemplo.com'
    );