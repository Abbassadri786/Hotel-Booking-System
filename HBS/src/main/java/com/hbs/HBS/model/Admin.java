package com.hbs.HBS.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Admin {
    private int id;
    
    @NotBlank(message = "Name is required")
    private String name;
 
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
 
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
    @NotBlank(message = "Mobile number is required")
    private String mobile;
 
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @NotBlank(message = "Password is required")
    private String password;

    public Admin(int id, String name, String email, String mobile, String password) {
        super();
    	this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", password=" + password + "]";
    }

}