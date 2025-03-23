-- Table 1: Customer
CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    dob DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 2: Room
CREATE TABLE room (
    roomId INT AUTO_INCREMENT PRIMARY KEY,
    roomNum VARCHAR(10) NOT NULL UNIQUE,
    roomType ENUM('Single', 'Double', 'Deluxe', 'Luxury', 'Suite') NOT NULL,
    totalPerson INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    isAvailable BOOLEAN NOT NULL,
    photo VARCHAR(255)
);

-- Table 3: Booking
CREATE TABLE booking (
    bookingId INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) NOT NULL,
    roomId INT NOT NULL,
    checkinDate DATE NOT NULL,
    checkoutDate DATE NOT NULL,
    totalPerson INT,
    payment DECIMAL(10, 2) NOT NULL,
    booking_status ENUM('BOOKED', 'CLOSED') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Confirmation_code CHAR(36) NOT NULL DEFAULT (UUID()),
	FOREIGN KEY (username) REFERENCES customer(username),
    FOREIGN KEY (roomId) REFERENCES room(roomId)
);

-- Table 4: Admin
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    mobile VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Table 5: Feedback
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5) NOT NULL,
    review TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
