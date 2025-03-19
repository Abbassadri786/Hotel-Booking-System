package com.hbs.HBS.model;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class Booking {
    private int bookingId;
    
    @NotBlank(message = "Username is required")
    private String username;

    @Positive(message = "Room ID must be positive")
    private int roomId;

    private Date checkinDate;
    private Date checkoutDate;

    @Positive(message = "Total persons must be positive")
    private int totalPerson;

    @Positive(message = "Payment must be positive")
    private double payment;

    @NotBlank(message = "Booking status is required")
    private String bookingStatus = "BOOKED"; // default value

    private Timestamp createdAt;
    
    @Size(min = 36, max = 36, message = "Confirmation code should be 36 characters long")
    private String confirmationCode;

    public Booking() {
        super();
    }

    @Override
    public String toString() {
        return "Booking [bookingId=" + bookingId + ", customerId=" + username + ", roomId=" + roomId + ", checkinDate=" + checkinDate + ", checkoutDate=" + checkoutDate + ", totalPerson=" + totalPerson + ", payment=" + payment + ", bookingStatus=" + bookingStatus + ", createdAt=" + createdAt + ", confirmationCode=" + confirmationCode + "]";
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(int totalPerson) {
        this.totalPerson = totalPerson;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
    	this.bookingStatus = bookingStatus != null ? bookingStatus : "BOOKED"; // Use provided value or default
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
    public Booking(int bookingId, String username, int roomId, Date checkinDate, Date checkoutDate, int totalPerson, double payment, String bookingStatus, String confirmationCode) {
        super();
        this.bookingId = bookingId;
        this.username = username;
        this.roomId = roomId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.totalPerson = totalPerson;
        this.payment = payment;
        this.bookingStatus = bookingStatus != null ? bookingStatus : "BOOKED"; // Use provided value or default
        this.confirmationCode = confirmationCode;
    }

    public Booking(int bookingId, String username, int roomId, Date checkinDate, Date checkoutDate, int totalPerson, double payment, String bookingStatus, Timestamp createdAt, String confirmationCode) {
        super();
        this.bookingId = bookingId;
        this.username = username;
        this.roomId = roomId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.totalPerson = totalPerson;
        this.payment = payment;
        this.bookingStatus = bookingStatus != null ? bookingStatus : "BOOKED"; // Use provided value or default
        this.createdAt = createdAt;
        this.confirmationCode = confirmationCode;
    }

}
