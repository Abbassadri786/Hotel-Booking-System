package com.hbs.HBS.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Room {
    private int roomId;

    @NotBlank(message = "Room number is required")
    @Size(max = 10, message = "Room number should be at most 10 characters long")
    private String roomNum;

    @NotBlank(message = "Room type is required")
    private String roomType;

    @Positive(message = "Total persons must be positive")
    private int totalPerson;

    @Positive(message = "Price must be positive")
    private double price;
    
    private boolean isAvailable = true; // Default value
    private String photo;
    
    public Room() {
    }

    public Room(String roomNum, String roomType, int totalPerson, double price, boolean isAvailable, String photo) {
        super();
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.totalPerson = totalPerson;
        this.price = price;
        this.isAvailable = true;
        this.photo = photo;
    }
    
    public Room(int roomId, String roomNum, String roomType, int totalPerson, double price, boolean isAvailable, String photo) {
        super();
        this.roomId = roomId;
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.totalPerson = totalPerson;
        this.price = price;
        this.isAvailable = isAvailable;
        this.photo = photo;
    }

    @Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomNum=" + roomNum + ", roomType=" + roomType + ", totalPerson="
				+ totalPerson + ", price=" + price + ", isAvailable=" + isAvailable + ", photo=" + photo + "]";
	}

	public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public int getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(int totalPerson) {
        this.totalPerson = totalPerson;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
