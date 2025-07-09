DROP TABLE IF EXISTS company_info;
DROP TABLE IF EXISTS notelines;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS product_taxes;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS users;

-- Tabla de usuarios
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       token VARCHAR(255) UNIQUE,
                       expiry_date DATETIME,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       dni VARCHAR(20) UNIQUE NOT NULL,
                       encrypted_password VARCHAR(255),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       birth_date DATETIME,
                       create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                       language ENUM('ESP', 'GAL', 'ENG') NOT NULL,
                       role ENUM('EMPLOYEE', 'CLERK', 'ADMIN') NOT NULL,
                       image LONGTEXT,
                       is_active BOOLEAN DEFAULT TRUE
);

-- Tabla de impuestos de productos
CREATE TABLE product_taxes (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               type VARCHAR(40) NOT NULL,
                               description VARCHAR(255) NOT NULL,
                               value FLOAT NOT NULL
);

-- Tabla de categorías de productos
CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            creator_id BIGINT NOT NULL,
                            CONSTRAINT fk_category_creator FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- Tabla de productos
CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          reference VARCHAR(255) NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          image TEXT,
                          data TEXT,
                          price FLOAT NOT NULL,
                          discount INT DEFAULT 0,
                          stock INT DEFAULT 0,
                          create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          tax_type_id BIGINT NOT NULL,
                          category_id BIGINT NOT NULL,
                          creator_id BIGINT NOT NULL,
                          CONSTRAINT fk_product_tax FOREIGN KEY (tax_type_id) REFERENCES product_taxes(id),
                          CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id),
                          CONSTRAINT fk_product_creator FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- Tabla de clientes
CREATE TABLE clients (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         dni VARCHAR(20) UNIQUE NOT NULL,
                         address VARCHAR(255),
                         city VARCHAR(100),
                         post_code BIGINT,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         phone_number BIGINT,
                         create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         creator_id BIGINT NOT NULL,
                         CONSTRAINT fk_client_creator FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- Tabla de facturas
CREATE TABLE invoices (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          subtotal FLOAT DEFAULT 0.0,
                          taxes FLOAT DEFAULT 0.0,
                          total FLOAT DEFAULT 0.0,
                          create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          client_id BIGINT NOT NULL,
                          creator_id BIGINT NOT NULL,
                          CONSTRAINT fk_invoice_client FOREIGN KEY (client_id) REFERENCES clients(id),
                          CONSTRAINT fk_invoice_creator FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- Tabla de notas (albaranes)
CREATE TABLE notes (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       subtotal FLOAT DEFAULT 0.0,
                       taxes FLOAT DEFAULT 0.0,
                       total FLOAT DEFAULT 0.0,
                       comment TEXT,
                       create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                       client_id BIGINT NOT NULL,
                       creator_id BIGINT NOT NULL,
                       invoice_id BIGINT,
                       CONSTRAINT fk_note_client FOREIGN KEY (client_id) REFERENCES clients(id),
                       CONSTRAINT fk_note_creator FOREIGN KEY (creator_id) REFERENCES users(id),
                       CONSTRAINT fk_note_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE SET NULL
);

-- Tabla de líneas de notas (detalles de productos en notas)
CREATE TABLE notelines (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           note_id BIGINT NOT NULL,
                           product_id BIGINT NOT NULL,
                           price FLOAT NOT NULL,
                           amount INT NOT NULL,
                           discount INT DEFAULT 0,
                           taxes FLOAT NOT NULL,
                           comment TEXT,
                           CONSTRAINT fk_noteline_note FOREIGN KEY (note_id) REFERENCES notes(id) ON DELETE CASCADE,
                           CONSTRAINT fk_noteline_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE company_info (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              address VARCHAR(255),
                              post_code BIGINT,
                              nif VARCHAR(50),
                              email VARCHAR(255),
                              web VARCHAR(255),
                              logo LONGTEXT
);