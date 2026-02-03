-- Insertar usuarios
INSERT INTO users (token, expiry_date, first_name, last_name, dni, encrypted_password, email, birth_date, create_date, language, role, image, is_active) VALUES
    (NULL, NULL, 'Arthur', 'Morgan', '12345678A', '$2a$10$TvCJgT2rfBWKsN164MsspuFiGTuchlZBVGWaNWy2wgxKWCi.pwdwG', 'arthur.morgan@van-der-linde.com', '1863-05-20', NOW(), 'ESP', 'ADMIN', 'user-1-photo.png', TRUE),
    (NULL, NULL, 'Daenerys', 'Targaryen', '56781234C', '$2a$10$TvCJgT2rfBWKsN164MsspuFiGTuchlZBVGWaNWy2wgxKWCi.pwdwG', 'daenerys.mother@dragons.westeros', '1995-08-25', NOW(), 'ENG', 'CLERK', 'user-2-photo.png', TRUE),
    (NULL, NULL, 'Joel', 'Miller', '87654321B', '$2a$10$TvCJgT2rfBWKsN164MsspuFiGTuchlZBVGWaNWy2wgxKWCi.pwdwG', 'joel.miller@jackson-county.org', '1981-09-26', NOW(), 'GAL', 'EMPLOYEE', 'user-3-photo.png', TRUE);

-- Insertar tipos de impuestos
INSERT INTO product_taxes (type, description, value) VALUES
    ('IVA General', 'Electrodomésticos, electricidad, vehículos, smartphones, etc.' , 21.0),
    ('IVA Reducido', 'Actividades culturales, entradas para el cine, medios de transporte o electricidad' , 10.0),
    ('IVA Superreducido', 'Bienes de primera necesidad, libros, periódicos y revistas, artículos de higiene femenina y preservativos, y viviendas de protección oficial' , 4.0);

-- Insertar categorías
INSERT INTO categories (name, description, create_date, creator_id) VALUES
    ('Tecnoloxía', 'Dispositivos electrónicos, smartphones e informática', NOW(), 1),
    ('Alimentación Básica', 'Produtos de primeira necesidade como pan, leite ou froita', NOW(), 1),
    ('Cultura e Lecer', 'Entradas, actividades culturais e medios de transporte', NOW(), 2),
    ('Electrodomésticos', 'Grandes e pequenos aparellos para o fogar', NOW(), 1),
    ('Hixiene e Saúde', 'Artigos de hixiene e produtos sanitarios básicos', NOW(), 2);

-- Insertar productos
INSERT INTO products (reference, name, description, image, data, price, discount, stock, create_date, tax_type_id, category_id, creator_id) VALUES
    ('REF-TECH-001', 'Monitor Gaming 27"', 'Monitor 4K con tecnoloxía LED e taxa de refresco de 144Hz', 'product-1-photo.png', 'product-1-data.pdf', 299.99, 5, 10, NOW(), 1, 1, 1),
    ('REF-FOOD-001', 'Aceite de Oliva Virxe', 'Garrafa de 5 litros de aceite de oliva de primeira prensa', NULL, NULL, 45.00, 0, 100, NOW(), 3, 2, 1),
    ('REF-CULT-001', 'Pack de 2 Entradas de Cine', 'Entradas válidas para calquera sesión e calquera día da semana', NULL, NULL, 16.00, 10, 50, NOW(), 2, 3, 2),
    ('REF-HOME-001', 'Frigorífico Combi', 'Frigorífico de aceiro inoxidable con eficiencia enerxética A+++', NULL, NULL, 650.00, 15, 2, NOW(), 1, 4, 1),
    ('REF-HEALTH-001', 'Oxímetro de Pulso Dixital', 'Dispositivo portátil de alta precisión para a medición de SpO2 e frecuencia cardíaca', NULL, NULL, 24.50, 0, 150, NOW(), 3, 5, 2),
    ('REF-TECH-002', 'Altavoz Bluetooth', 'Altavoz portátil resistente ao auga con 20W de potencia', NULL, NULL, 59.90, 0, 5, NOW(), 1, 1, 1),
    ('REF-CULT-002', 'Libro de Cociña Galega', 'Edición de luxo con máis de 500 receitas tradicionais', NULL, NULL, 35.00, 10, 1, NOW(), 2, 3, 2),
    ('REF-HEALTH-002', 'Xel Hidroalcólico', 'Bote de 1 litro con dosificador para desinfección de mans', NULL, NULL, 3.95, 5, 20, NOW(), 3, 5, 1);

-- Insertar clientes
INSERT INTO clients (first_name, last_name, dni, address, city, post_code, email, phone_number, create_date, creator_id) VALUES
    ('Laura', 'González', '11111111A', 'Calle Real 12', 'Madrid', 28001, 'laura.gonzalez@example.com', 612345678, NOW(), 1),
    ('Carlos', 'Pérez', '22222222B', 'Av. del Mar 45', 'Valencia', 46010, 'carlos.perez@example.com', 678901234, NOW(), 2),
    ('Marta', 'López', '33333333C', 'Rúa Nova 33', 'Santiago', 15782, 'marta.lopez@example.com', 699998877, NOW(), 1);

INSERT INTO invoices (subtotal, taxes, total, create_date, client_id, creator_id) VALUES
    (370.45, 73.25, 443.70, NOW(), 1, 1),
    (48.56, 1.94, 50.50, NOW(), 2, 2);

INSERT INTO notes (subtotal, taxes, total, comment, create_date, client_id, creator_id, invoice_id) VALUES
    (329.14, 69.12, 398.26, 'Entrega urxente en oficina', NOW(), 1, 1, 1),
    (48.56, 1.94, 50.50, '', NOW(), 2, 2, 2),
    (469.62, 98.62, 568.24, 'Mercancía pendente de revisión', NOW(), 3, 2, null),
    (41.31, 4.13, 45.44, 'Recollida en tenda por cliente', NOW(), 1, 1, 1);

INSERT INTO notelines (note_id, product_id, price, amount, discount, taxes, comment) VALUES
     (1, 1, 284.99, 1, 5, 56.86, 'Revisar píxeles mortos antes do envío'),
     (1, 6, 59.90, 1, 0, 12.58, ''),
     (2, 2, 45.00, 1, 0, 1.80, 'Lote caducidade 2027'),
     (2, 8, 3.75, 1, 5, 0.14, ''),
     (3, 4, 552.50, 1, 15, 98.62, 'Golpe leve no lateral dereito - Outlet'),
     (4, 3, 14.40, 1, 10, 1.30, 'Entregado en despacho de dirección'),
     (4, 7, 31.50, 1, 10, 2.83, '');

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