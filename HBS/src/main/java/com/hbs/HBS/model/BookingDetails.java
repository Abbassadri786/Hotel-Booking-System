package com.hbs.HBS.model;

import java.sql.Date;
import java.sql.Timestamp;


public class BookingDetails {
    private int bookingId;
    private String username;
    private int roomId;
    private Date checkinDate;
    private Date checkoutDate;
    private int totalPerson;
    private double payment;
    private String bookingStatus;
    private Timestamp createdAt;
    private String confirmationCode;
    private String roomNum;
    private String roomType;
    
	public BookingDetails(int bookingId, String username, int roomId, Date checkinDate, Date checkoutDate,
			int totalPerson, double payment, String bookingStatus, Timestamp createdAt, String confirmationCode,
			String roomNum, String roomType) {
		super();
		this.bookingId = bookingId;
		this.username = username;
		this.roomId = roomId;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.totalPerson = totalPerson;
		this.payment = payment;
		this.bookingStatus = bookingStatus;
		this.createdAt = createdAt;
		this.confirmationCode = confirmationCode;
		this.roomNum = roomNum;
		this.roomType = roomType;
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
		this.bookingStatus = bookingStatus;
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

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Override
	public String toString() {
		return "BookingDetails [bookingId=" + bookingId + ", username=" + username + ", roomId=" + roomId
				+ ", checkinDate=" + checkinDate + ", checkoutDate=" + checkoutDate + ", totalPerson=" + totalPerson
				+ ", payment=" + payment + ", bookingStatus=" + bookingStatus + ", createdAt=" + createdAt
				+ ", confirmationCode=" + confirmationCode + ", roomNum=" + roomNum + ", roomType=" + roomType + "]";
	}
	
    
    
    
    

}
