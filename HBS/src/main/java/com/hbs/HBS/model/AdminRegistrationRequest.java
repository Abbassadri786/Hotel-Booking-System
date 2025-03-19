package com.hbs.HBS.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class AdminRegistrationRequest {
    @NotBlank(message = "Access code is required")
    private String accessCode;

    @Valid
    private Admin admin;

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
