-- Insert values for the customer table
INSERT INTO customer (username, email, password, phone, gender, dob) VALUES
('john_doe', 'john.doe@example.com', 'password123', '1234567890', 'Male', '1990-01-01'),
('jane_doe', 'jane.doe@example.com', 'password456', '0987654321', 'Female', '1992-05-15');

-- Insert values for the room table
INSERT INTO room (roomNum, roomType, totalPerson, price, isAvailable, photo) VALUES
('101', 'Single', 1, 1000.00, TRUE, 'https://images.unsplash.com/photo-1631049035182-249067d7618e?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('102', 'Double', 2, 1500.00, TRUE, 'https://images.unsplash.com/photo-1618773928121-c32242e63f39?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('103', 'Deluxe', 2, 2000.00, TRUE, 'https://images.unsplash.com/photo-1505692952047-1a78307da8f2?q=80&w=3096&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('104', 'Luxury', 3, 2500.00, TRUE, 'https://images.unsplash.com/photo-1505692433770-36f19f51681d?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('105', 'Suite', 4, 3000.00, TRUE, 'https://images.unsplash.com/photo-1595576508898-0ad5c879a061?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('201', 'Single', 1, 1000.00, TRUE, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');

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
('Anonymous', 3, 'Average stay, could be improved.'),
('Emily Smith', 5, 'Loved the ambiance and hospitality!'),
('Michael Brown', 4, 'Comfortable stay, but food options were limited.'),
('Sophia Johnson', 3, 'The room was fine, but cleanliness could improve.'),
('Chris Wilson', 5, 'Exceptional service, highly recommend!'),
('Olivia Martinez', 2, 'Unfriendly staff and delayed check-in process.');


