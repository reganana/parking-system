-- User table
INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, USER_TYPE, TOTAL_FINE)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 'student', 0.0),
    ('Jane', 'Smith', 'jane.smith@example.com', 'staff', 15.0),
    ('Alice', 'Johnson', 'alice.johnson@example.com', 'visitor', 0.0);


-- Parking lots table
INSERT INTO PARKING_LOTS (LOT_ID, LOT_NAME, TOTAL_SPOTS, VISITOR_ALLOWED, HOURLY_RATE, LOCATION)
VALUES
    -- Parking lot ID, Parking lot name, Total number of spots, Whether visitors are allowed, Hourly rate, Parking lot location
    (1, 'Lot A', 100, TRUE, 5.0, 'North Campus'),
    (2, 'Lot B', 50, FALSE, 3.0, 'East Campus'),
    (3, 'Lot C', 75, TRUE, 4.0, 'West Campus');


INSERT INTO PARKING_PERMITS (TRANSPONDER_NUMBER, VALID_FROM, VALID_UNTIL, LICENSE_PLATE, PAYMENT_METHOD, PAYROLL_NUM)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', '2024-01-01', '2024-12-31', 'ABC123', 'direct', NULL),
    ('123e4567-e89b-12d3-a456-426614174000', '2024-01-01', '2024-06-30', 'DEF456', 'payroll', '101');

INSERT INTO VOUCHER (VOUCHER, VALID_FROM, VALID_UNTIL)
VALUES
    ('VOUCHER001', '2024-03-01', '2024-03-05'),
    ('VOUCHER002', '2024-03-02', '2024-03-07');

INSERT INTO PARKING_VIOLATIONS (VIOLATION_ID, VIOLATION_TIME, LICENSE_PLATE, FINE_AMOUNT, IS_PAID, OFFICER_ID, LOT_ID)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', '2024-03-01T13:00:00', 'ABC123', 50.0, FALSE, 2, 1),
    ('123e4567-e89b-12d3-a456-426614174000', '2024-03-02T10:00:00', 'XYZ789', 75.0, TRUE, 1, 3);

INSERT INTO LOT_OCCUPANCY (ID, LOT_ID, TIMESTAMP, CURRENT_OCCUPANCY)
VALUES
    (1, 1, '2024-03-01 10:00:00', 30),
    (2, 1, '2024-03-01 11:00:00', 45),
    (3, 2, '2024-03-02 09:00:00', 20);
