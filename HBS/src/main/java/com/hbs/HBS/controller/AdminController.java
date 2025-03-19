package com.hbs.HBS.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbs.HBS.dto.AdminLoginRequest;
import com.hbs.HBS.model.Admin;
import com.hbs.HBS.model.AdminRegistrationRequest;
import com.hbs.HBS.service.AdminService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;
    private final String predefinedAccessCode = "admin@1234";

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminRegistrationRequest request) {
        if (request == null || request.getAccessCode() == null || request.getAdmin() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }

        if (!predefinedAccessCode.equals(request.getAccessCode())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid access code");
        }

        adminService.registerAdmin(request.getAdmin());
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@Valid @RequestBody AdminLoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getName() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }

        Admin loggedInAdmin = adminService.loginAdmin(loginRequest.getName(), loginRequest.getPassword());
        if (loggedInAdmin != null) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

}