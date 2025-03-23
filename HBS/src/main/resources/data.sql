-- Insert values for the customer table
INSERT INTO customer (username, email, password, phone, gender, dob) VALUES
('john_doe', 'john.doe@example.com', 'password123', '1234567890', 'Male', '1990-01-01'),
('jane_doe', 'jane.doe@example.com', 'password456', '0987654321', 'Female', '1992-05-15');

-- Insert values for the room table
INSERT INTO room (roomNum, roomType, totalPerson, price, isAvailable, photo) VALUES
('101', 'Single', 1, 1000.00, TRUE, 'https://threeswans.co.uk/wp-content/uploads/2021/03/Room-34-Alternate-copy-1024x1024.jpg'),
('102', 'Double', 2, 1500.00, TRUE, 'https://threeswans.co.uk/wp-content/uploads/2021/03/Room-64-copy-1024x1024.jpg'),
('103', 'Deluxe', 2, 2000.00, TRUE, 'https://threeswans.co.uk/wp-content/uploads/2021/02/Bridal-Suite-Three-Swans-Market-Harborough-1024x1024.jpg'),
('104', 'Luxury', 3, 2500.00, TRUE, 'https://threeswans.co.uk/wp-content/uploads/2021/03/Room-102-copy-1024x1024.jpg'),
('105', 'Suite', 4, 3000.00, TRUE, 'https://threeswans.co.uk/wp-content/uploads/2021/03/Room-80-copy-1024x1024.jpg');


-- Insert values for the booking table
INSERT INTO booking (username, roomId, checkinDate, checkoutDate, totalPerson, payment, booking_status, Confirmation_code) VALUES
('john_doe', 1, '2023-04-01', '2023-04-05', 1, 5000.00, 'BOOKED', '123e4567-e89b-12d3-a456-426614174000'),
('jane_doe', 3, '2023-04-10', '2023-04-15', 2, 10000.00, 'BOOKED', '123e4567-e89b-12d3-a456-426614174001');

-- Insert values for the admin table
INSERT INTO admin (name, email, mobile, password) VALUES
('Admin1', 'admin1@example.com', '1122334455', 'adminpass1'),
('Admin2', 'admin2@example.com', '2233445566', 'adminpass2');

-- Insert values for the feedback table
INSERT INTO feedback (name, rating, review) VALUES
('John Doe', 5, 'Excellent service!'),
('Jane Doe', 4, 'Very good experience.'),
('Anonymous', 3, 'Average stay, could be improved.');

