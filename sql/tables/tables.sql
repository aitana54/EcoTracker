-- Drop tables if exist (en el orden correcto por FK)
DROP TABLE IF EXISTS sostenibilitat;
DROP TABLE IF EXISTS actions;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- Create categories table
CREATE TABLE IF NOT EXISTS categories (
    categoria_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(255) unique NOT NULL,
    c02_estimat DECIMAL(10,3)
) ENGINE=InnoDB;

-- Create actions table
CREATE TABLE IF NOT EXISTS actions (
    id_action INT AUTO_INCREMENT PRIMARY KEY,
    time_of_action VARCHAR(255),
    action_performed text
) ENGINE=InnoDB;

-- Create sostenibilitat table with foreign key to categories
CREATE TABLE IF NOT EXISTS sostenibilitat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    fecha DATETIME,
    categoria INT,
    km DECIMAL(10,3),
    kg DECIMAL(10,3),
    llums_hora_estimada DECIMAL(10,3),
    teletreball INT,
    descripcio TEXT,
    co2_total DECIMAL(10,3),
    CONSTRAINT fk_categoria
        FOREIGN KEY (categoria)
        REFERENCES categories(categoria_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- create table for logins
CREATE TABLE IF NOT EXISTS users (
	id INT AUTO_INCREMENT PRIMARY KEY,
    username varchar(50) unique,
    passwd varchar(255)
)ENGINE=InnoDB;
