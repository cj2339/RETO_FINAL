DROP DATABASE IF EXISTS imperialMaritime;
CREATE DATABASE imperialMaritime;
USE imperialMaritime;

CREATE TABLE administrator(
	name_admin VARCHAR(30) PRIMARY KEY,
    password_admin VARCHAR(30)
);

INSERT INTO administrator VALUES
	('Iker','soyMaloAlPadel26'),
    ('Aritz','aritzetePenegordo33'),
    ('Santi','villalibre9'),
    ('Etna','123');

CREATE TABLE cruise(
	cod_cruise INTEGER AUTO_INCREMENT PRIMARY KEY,
    name_cruise VARCHAR(30),
    type_cruise ENUM('luxury','premium','family','expedition'),
	num_rooms INTEGER,
	capacity_max INTEGER
);

INSERT INTO cruise(name_cruise, type_cruise, num_rooms, capacity_max) VALUES 
('North Sea', 'luxury', 200, 1000),
('Tropical Adventure', 'family', 150, 800),
('Antarctic Ice', 'expedition', 50, 150),
('Mediterranean Diamond', 'luxury', 300, 1500),
('Coastal Breeze', 'family', 100, 400);

CREATE TABLE worker(
	id_worker CHAR(9) PRIMARY KEY,
    service ENUM('captain','cook','guide','receptionist'),
    name_worker VARCHAR(30),
    surname_worker VARCHAR(30),
    hiring_date DATE,
    phone_number CHAR(9),
    email VARCHAR(30),
    age INTEGER CHECK(age>18),
    language_worker ENUM('Spanish','English','French','German','Chinese','Arabic'),
    cod_cruise INTEGER,
    FOREIGN KEY(cod_cruise) REFERENCES cruise(cod_cruise) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO worker VALUES 
('12345678A', 'cook', 'Taylor', 'Robert', '2023-01-15', '600111222', 'taylor.r@email.com', 34, 'Spanish', 1),
('12345678B', 'guide', 'Wilson', 'Louis', '2022-11-05', '600333444', 'wilson.l@email.com', 28, 'English', 1),
('12345678C', 'receptionist', 'Anderson', 'Laura', '2024-02-10', '600555666', 'anderson.l@email.com', 41, 'French', 2),
('12345678D', 'captain', 'Thomas', 'Frank', '2020-05-20', '600777888', 'thomas.f@email.com', 52, 'German', 3),
('12345678F', 'guide', 'White', 'Martha', '2021-08-12', '600999000', 'white.m@email.com', 30, 'Chinese', 4);

CREATE TABLE client(
	id_client CHAR(9) PRIMARY KEY,
    name_client VARCHAR(30),
    surname_client VARCHAR(30),
    age_client INTEGER
);

INSERT INTO client VALUES 
('11111111A', 'John', 'Smith', 35),
('22222222B', 'Mary', 'Johnson', 28),
('33333333C', 'Charles', 'Brown', 45),
('44444444D', 'Anna', 'Miller', 52),
('55555555E', 'Helen', 'Davis', 31);

CREATE TABLE book(
	id_client CHAR(9),
	cod_cruise int,
    originCity VARCHAR(30),
    destinationCity VARCHAR(30),
    startDate DATE,
    endDate DATE,
    basePrice DOUBLE,
    PRIMARY KEY(cod_cruise, id_client, startDate),
    FOREIGN KEY(cod_cruise) REFERENCES cruise(cod_cruise) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(id_client) REFERENCES client(id_client) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO book VALUES 
('11111111A', 1, 'London', 'Oslo', '2026-06-01', '2026-06-15', 1200.50),
('22222222B', 2, 'Miami', 'Nassau', '2026-07-10', '2026-07-17', 850.00),
('33333333C', 1, 'London', 'Oslo', '2026-06-01', '2026-06-15', 1200.50),
('44444444D', 4, 'Athens', 'Rome', '2026-08-05', '2026-08-12', 1500.00),
('55555555E', 5, 'Lisbon', 'Malaga', '2026-09-20', '2026-09-25', 450.00);