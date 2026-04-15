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
('Mediterranean Diamond', 'luxury', 300, 1000),
('Coastal Breeze', 'family', 100, 800);


CREATE TABLE worker(
    id_worker CHAR(9) PRIMARY KEY,
    service ENUM('captain','cook','guide','receptionist'),
    name_worker VARCHAR(30),
    surname_worker VARCHAR(30),
    hiring_date DATE,
    phone_number CHAR(9),
    email VARCHAR(30),
    age INTEGER CHECK(age > 18),
    spanish_language BOOLEAN, 
    english_language BOOLEAN,
    cod_cruise INTEGER,
    FOREIGN KEY(cod_cruise) REFERENCES cruise(cod_cruise) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO worker VALUES 
('12345678A', 'cook', 'Taylor', 'Robert', '2023-01-15', '600111222', 'taylor.r@email.com', 34, true, true, 1),
('12345678B', 'guide', 'Wilson', 'Louis', '2022-11-05', '600333444', 'wilson.l@email.com', 28, true, false, 1),
('12345678C', 'receptionist', 'Anderson', 'Laura', '2024-02-10', '600555666', 'anderson.l@email.com', 41, true, false, 2),
('12345678D', 'captain', 'Thomas', 'Frank', '2020-05-20', '600777888', 'thomas.f@email.com', 52, true, true, 3),
('12345678F', 'guide', 'White', 'Martha', '2021-08-12', '600999000', 'white.m@email.com', 30, true, true, 4);

CREATE TABLE client(
    id_client CHAR(9) PRIMARY KEY,
    name_client VARCHAR(30),
    surname_client VARCHAR(30),
    age_client INTEGER,
    phone_client INTEGER,
    email_client VARCHAR(40)
);

INSERT INTO client VALUES 
('11111111A', 'John', 'Smith', 35, 604923059, 'johnsmith@gmail.com'),
('22222222B', 'Mary', 'Johnson', 28, 693462061, 'maryjohnson@gmail.com'),
('33333333C', 'Charles', 'Brown', 45, 629403819, 'charlesbrown@gmail.com'),
('44444444D', 'Anna', 'Miller', 52, 631942840, 'annamiller@gmail.com'),
('55555555E', 'Helen', 'Davis', 31, 667385867, 'helendavis@gmail.com');

CREATE TABLE book(
    id_client CHAR(9),
    cod_cruise INT,
    originCity VARCHAR(30),
    destinationCity VARCHAR(30),
    startDate DATE,
    endDate DATE,
    room_number INT,
    basePrice DOUBLE,
    finalPrice DOUBLE,
    PRIMARY KEY(cod_cruise, id_client, startDate),
    FOREIGN KEY(cod_cruise) REFERENCES cruise(cod_cruise)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(id_client) REFERENCES client(id_client)
        ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO book VALUES 
('11111111A', 1, 'London', 'Oslo', '2026-06-01', '2026-06-15', 1, 1200.00, 1200.50),
('22222222B', 2, 'Miami', 'Nassau', '2026-07-10', '2026-07-17', 1, 850.00, 850.00),
('33333333C', 1, 'London', 'Oslo', '2026-06-01', '2026-06-15', 1, 1200.00, 1200.50),
('44444444D', 4, 'Athens', 'Rome', '2026-08-05', '2026-08-12', 1, 1500.00, 1500.00),
('55555555E', 5, 'Lisbon', 'Malaga', '2026-09-20', '2026-09-25', 1, 450.00, 450.00);


use imperialmaritime;
-- ---------------------------------------------------------
-- 1. PROCEDURE: List workers by cruise
-- ---------------------------------------------------------
DROP PROCEDURE IF EXISTS p_list_workers_by_cruise;
DELIMITER //
CREATE PROCEDURE p_list_workers_by_cruise(IN p_cod_cruise INT)
BEGIN
    DECLARE v_name VARCHAR(30);
    DECLARE v_surname VARCHAR(30);
    DECLARE v_service VARCHAR(20);
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_exists INT DEFAULT 0;
    DECLARE cur_workers CURSOR FOR
        SELECT name_worker, surname_worker, service
        FROM worker
        WHERE cod_cruise = p_cod_cruise;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    -- Cruise exists?
    SELECT COUNT(*) INTO v_exists
    FROM cruise
    WHERE cod_cruise = p_cod_cruise;
    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERROR: Cruise code does not exist';
    END IF;
    OPEN cur_workers;
    read_loop: LOOP
        FETCH cur_workers INTO v_name, v_surname, v_service;
        IF done THEN LEAVE read_loop; END IF;
        SELECT CONCAT('Worker: ', v_name, ' ', v_surname,'\nService: ', v_service) AS 'Cruise ship crew';
    END LOOP;
    CLOSE cur_workers;
END //
DELIMITER ;


use imperialmaritime;
-- ---------------------------------------------------------
-- 2. PROCEDURE: Make a booking
-- ---------------------------------------------------------
DROP PROCEDURE IF EXISTS p_create_booking;
DELIMITER //
CREATE PROCEDURE p_create_booking(
    IN p_id_client CHAR(9),
    IN p_cod_cruise INT,
    IN p_originCity VARCHAR(30),
    IN p_destinationCity VARCHAR(30),
    IN p_startDate DATE,
    IN p_endDate DATE,
    IN p_room_number INT
)
BEGIN
    DECLARE v_count INT DEFAULT 0;
    DECLARE v_capacity INT;
    DECLARE v_booked INT;
    DECLARE v_basePrice DOUBLE;
    DECLARE v_finalPrice DOUBLE;
    DECLARE v_max_rooms INT;
    -- ERROR HANDLER
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Unexpected database error during booking creation';
    END;

    START TRANSACTION;
    -- 1) VALIDATE CUSTOMER
    SELECT COUNT(*) INTO v_count FROM client WHERE id_client = p_id_client;
    IF v_count = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Client does not exist';
    END IF;
    -- 2) VALIDATE CRUISE
    SELECT COUNT(*) INTO v_count FROM cruise WHERE cod_cruise = p_cod_cruise;
    IF v_count = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cruise does not exist';
    END IF;
    -- 3) CHECK DATES
    IF p_startDate >= p_endDate THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Invalid date range';
    END IF;
    -- 4) AT LEAST 15 DAYS' NOTICE
    IF DATEDIFF(p_startDate, CURDATE()) < 15 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Bookings must be made at least 15 days in advance';
    END IF;
    -- 5) THE CUSTOMER CANNOT HAVE ANOTHER BOOKING FOR THOSE DATES
    SELECT COUNT(*) INTO v_count
    FROM book
    WHERE id_client = p_id_client
      AND (
            (startDate <= p_startDate AND endDate >= p_startDate) OR
            (startDate <= p_endDate AND endDate >= p_endDate) OR
            (p_startDate <= startDate AND p_endDate >= endDate)
          );

    IF v_count > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Client already has a booking during these dates';
    END IF;
    -- 6) THE CRUISE SHIP CANNOT OPERATE ON ANY OTHER ROUTE ON THOSE DATES
    SELECT COUNT(*) INTO v_count
    FROM book
    WHERE cod_cruise = p_cod_cruise
      AND (
            (startDate <= p_startDate AND endDate >= p_startDate) OR
            (startDate <= p_endDate AND endDate >= p_endDate) OR
            (p_startDate <= startDate AND p_endDate >= endDate)
          );

    IF v_count > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cruise is already assigned to another route during these dates';
    END IF;
    -- 7) CHECK ROOM AVAILABILITY BY CRUISE TYPE
    SELECT 
        CASE type_cruise
            WHEN 'luxury' THEN 1000
            WHEN 'premium' THEN 500
            WHEN 'family' THEN 800
            WHEN 'expedition' THEN 150
        END
    INTO v_max_rooms
    FROM cruise
    WHERE cod_cruise = p_cod_cruise;

    IF p_room_number > v_max_rooms THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Room number exceeds allowed range for this cruise type';
    END IF;
    -- 8) VERIFY THE CRUISE SHIP’S TOTAL CAPACITY
    SELECT capacity_max INTO v_capacity FROM cruise WHERE cod_cruise = p_cod_cruise;
    SELECT COUNT(*) INTO v_booked
    FROM book
    WHERE cod_cruise = p_cod_cruise;
    IF v_booked >= v_capacity THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cruise is fully booked';
    END IF;
    -- 9) CONFIRM ROOM (max. 5 people per room)
    SELECT COUNT(*) INTO v_count
    FROM book
    WHERE cod_cruise = p_cod_cruise
      AND room_number = p_room_number;
    IF v_count >= 5 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Selected room is full';
    END IF;
    -- 10) CALCULATE THE BASIC PRICE ACCORDING TO THE TYPE OF CRUISE
    SELECT 
        CASE type_cruise
            WHEN 'luxury' THEN 1200
            WHEN 'premium' THEN 900
            WHEN 'family' THEN 700
            WHEN 'expedition' THEN 1500
        END
    INTO v_basePrice
    FROM cruise
    WHERE cod_cruise = p_cod_cruise;
    -- 11) CALCULATE FINAL PRICE (function)
    SELECT fn_calculate_final_price(p_id_client, p_cod_cruise, v_basePrice)
    INTO v_finalPrice;
    -- 12) BOOK INSERT
    INSERT INTO book VALUES(
        p_id_client,
        p_cod_cruise,
        p_originCity,
        p_destinationCity,
        p_startDate,
        p_endDate,
        p_room_number,
        v_basePrice,
        v_finalPrice
    );
    COMMIT;
END//
DELIMITER ;


use imperialmaritime;
-- --------------------------------------------------------------
-- 3. FUNCTION: Function with discount logic (age + cruise type)
-- --------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_calculate_final_price;
DELIMITER //
CREATE FUNCTION fn_calculate_final_price(
    p_id_client VARCHAR(20),
    p_cod_cruise INT,
    p_basePrice DOUBLE
) RETURNS DOUBLE
DETERMINISTIC
BEGIN
    DECLARE v_age INT;
    DECLARE v_type VARCHAR(20);
    DECLARE v_discount DOUBLE DEFAULT 0.0;
    DECLARE v_final DOUBLE;
    IF p_basePrice IS NULL OR p_basePrice <= 0 THEN
        RETURN NULL;
    END IF;
    SELECT age_client INTO v_age
    FROM client
    WHERE id_client = p_id_client
    LIMIT 1;
    IF v_age IS NULL THEN
        RETURN NULL;
    END IF;

    SELECT type_cruise INTO v_type
    FROM cruise
    WHERE cod_cruise = p_cod_cruise
    LIMIT 1;
    IF v_type IS NULL THEN
        RETURN NULL;
    END IF;
    IF v_age >= 65 THEN
        SET v_discount = 0.15;
    ELSEIF v_type = 'family' AND v_age <= 12 THEN
        SET v_discount = 0.30;
    ELSEIF v_type = 'expedition' THEN
        SET v_discount = 0.10;
    END IF;
    SET v_final = p_basePrice * (1 - v_discount);
    RETURN ROUND(v_final, 2);
END //
DELIMITER ;


use imperialmaritime;
-- ---------------------------------------------------------
-- 4. PROCEDURE: Occupancy report (cursor + calculations)
-- ---------------------------------------------------------
DROP PROCEDURE IF EXISTS p_cruise_occupancy_report;
DELIMITER //
CREATE PROCEDURE p_cruise_occupancy_report()
BEGIN
    DECLARE v_cod VARCHAR(20);
    DECLARE v_name VARCHAR(50);
    DECLARE v_capacity INT;
    DECLARE v_booked INT;
    DECLARE v_percent DECIMAL(6,2);
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur CURSOR FOR 
        SELECT c.cod_cruise, c.name_cruise, c.capacity_max, COUNT(b.id_client) AS booked
        FROM cruise c
        LEFT JOIN book b ON c.cod_cruise = b.cod_cruise
        GROUP BY c.cod_cruise, c.name_cruise, c.capacity_max;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_cod, v_name, v_capacity, v_booked;
        IF done THEN LEAVE read_loop; END IF;

        -- Avoid division by zero
        IF v_capacity IS NULL OR v_capacity = 0 THEN
            SET v_percent = 0;
        ELSE
            SET v_percent = (v_booked / v_capacity) * 100;
        END IF;

        SELECT CONCAT('Cruise: ', v_name, ' (', v_cod, ')','\nOccupation: ', v_booked, '/', v_capacity,' (', ROUND(v_percent,2), '%)') AS 'Occupancy report';
    END LOOP;
    CLOSE cur;
END //
DELIMITER ;


use imperialmaritime;
-- -----------------------------------------------------------------
-- 5. PROCEDURE: Cancel a booking in accordance with business rules
-- -----------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_cancel_booking;
DELIMITER //
CREATE PROCEDURE sp_cancel_booking(
    IN p_id_client VARCHAR(20),
    IN p_cod_cruise VARCHAR(20),
    OUT p_result VARCHAR(100)
)
sp_cancel_booking: BEGIN
    DECLARE v_start DATE;
    DECLARE no_row_found BOOL DEFAULT FALSE;
    -- Handler for SELECT queries with no results
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_row_found = TRUE;
    -- General handler for SQL errors
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SET p_result = 'ERROR: The booking could not be cancelled';
    END;
    START TRANSACTION;
    -- Search for the booking
    SELECT startDate INTO v_start
    FROM book
    WHERE id_client = p_id_client AND cod_cruise = p_cod_cruise
    LIMIT 1;

    IF no_row_found OR v_start IS NULL THEN
        SET p_result = 'ERROR: Booking not found';
        ROLLBACK; LEAVE sp_cancel_booking;
    END IF;
    -- Business rule: minimum 15 days
    IF DATEDIFF(v_start, CURDATE()) < 15 THEN
        SET p_result = 'ERROR: Cancellations must be made at least 15 days in advance';
        ROLLBACK; LEAVE sp_cancel_booking;
    END IF;
    -- Cancel booking
    DELETE FROM book
    WHERE id_client = p_id_client AND cod_cruise = p_cod_cruise;

    COMMIT;
    SET p_result = 'Booking successfully cancelled';
END //
DELIMITER ;


use imperialmaritime;
-- -----------------------------------------------------------------
-- 6. PROCEDURE: A customer's booking history
-- -----------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_client_booking_history;
DELIMITER //
CREATE PROCEDURE sp_client_booking_history(IN p_id_client VARCHAR(20))
BEGIN
    DECLARE v_exists INT DEFAULT 0;

    SELECT COUNT(*) INTO v_exists
    FROM client
    WHERE id_client = p_id_client;

    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The customer does not exist';
    END IF;

    SELECT
        b.cod_cruise, c.name_cruise, b.startDate, b.endDate, b.basePrice AS precio_base, b.finalPrice AS precio_final, ROUND(b.basePrice - b.finalPrice, 2) AS descuento_aplicado
    FROM book b 
    JOIN cruise c ON b.cod_cruise = c.cod_cruise
    WHERE b.id_client = p_id_client;
END //
DELIMITER ;


use imperialmaritime;
-- -----------------------------------------------------------------
-- 7. PROCEDURE: Revenue statistics by cruise type
-- -----------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_revenue_by_type;
DELIMITER //
CREATE PROCEDURE sp_revenue_by_type()
BEGIN
    DECLARE v_type VARCHAR(20);
    DECLARE v_total DOUBLE;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur CURSOR FOR
        SELECT c.type_cruise, IFNULL(SUM(b.finalPrice), 0)
        FROM cruise c
        LEFT JOIN book b ON c.cod_cruise = b.cod_cruise
        GROUP BY c.type_cruise;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_type, v_total;
        IF done THEN LEAVE read_loop; END IF;

        SELECT CONCAT('Type: ', v_type,'\nTotal revenue: ', ROUND(v_total, 2), ' €') AS 'Revenue by cruise type';
    END LOOP;
    CLOSE cur;
END //
DELIMITER ;


use imperialmaritime;
-- -----------------------------------------------------------------
-- 8. FUNCTIONS: Availability of places
-- -----------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_get_available_seats;
DELIMITER //
CREATE FUNCTION fn_get_available_seats(p_cod_cruise VARCHAR(20))
RETURNS INT
	DETERMINISTIC
BEGIN
    DECLARE v_capacity INT DEFAULT NULL;
    DECLARE v_booked INT DEFAULT 0;
    -- Achieve maximum capacity
    SELECT capacity_max INTO v_capacity
    FROM cruise
    WHERE cod_cruise = p_cod_cruise
    LIMIT 1;
    -- If there is no cruise
    IF v_capacity IS NULL THEN
        RETURN -1;
    END IF;
    -- Check availability
    SELECT COUNT(*) INTO v_booked
    FROM book
    WHERE cod_cruise = p_cod_cruise;
    RETURN v_capacity - v_booked;
END //
DELIMITER ;

use imperialmaritime;
-- -----------------------------------------------------------------
-- 9. FUNCTIONS: Cruise duration in days (to be displayed in the app)
-- -----------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_calculate_trip_duration;
DELIMITER //
CREATE FUNCTION fn_calculate_trip_duration(p_cod_cruise VARCHAR(20), p_id_client VARCHAR(20))
RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE v_start DATE DEFAULT NULL;
    DECLARE v_end DATE DEFAULT NULL;
    -- View booking details
    SELECT startDate, endDate
    INTO v_start, v_end
    FROM book
    WHERE cod_cruise = p_cod_cruise
      AND id_client = p_id_client
    LIMIT 1;
    -- If there is no booking
    IF v_start IS NULL OR v_end IS NULL THEN
        RETURN -1;
    END IF;
    RETURN DATEDIFF(v_end, v_start) + 1;
END //
DELIMITER ;


use imperialmaritime;
-- -----------------------------------------------------------------
-- 10. FUNCTIONS: Total expenditure by a customer
-- -----------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_get_client_total_spent;
DELIMITER //
CREATE FUNCTION fn_get_client_total_spent(p_id_client VARCHAR(20))
RETURNS DOUBLE
DETERMINISTIC
BEGIN
    DECLARE v_exists INT DEFAULT 0;
    DECLARE v_total DOUBLE DEFAULT 0.0;

    SELECT COUNT(*) INTO v_exists
    FROM client
    WHERE id_client = p_id_client;

    IF v_exists = 0 THEN
        RETURN -1;
    END IF;

    SELECT IFNULL(SUM(finalPrice), 0)
    INTO v_total
    FROM book
    WHERE id_client = p_id_client;
    RETURN ROUND(v_total, 2);
END //
DELIMITER ;