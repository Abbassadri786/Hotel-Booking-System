package com.hbs.HBS.service;

import org.springframework.stereotype.Service;
import com.hbs.HBS.model.Admin;
import com.hbs.HBS.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Register a new admin
    public void registerAdmin(Admin admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Admin with this email already exists");
        }
        adminRepository.save(admin);
    }

    // Admin login
    public Admin loginAdmin(String name, String password) {
        Admin admin = adminRepository.login(name, password);
        if (admin == null) {
            throw new IllegalArgumentException("Invalid name or password");
        }
        return admin;
    }
}
