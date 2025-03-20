package com.hbs.HBS.service;

import com.hbs.HBS.model.Admin;
import com.hbs.HBS.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    private AdminRepository adminRepository;
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        adminRepository = mock(AdminRepository.class); // Mock AdminRepository
        adminService = new AdminService(adminRepository); // Use mocked repository in service
    }

    @Test
    public void testRegisterAdminSuccess() {
        Admin admin = new Admin(1, "admin_name", "admin@example.com", "1234567890", "securePassword");

        // Mock repository behavior
        when(adminRepository.existsByEmail(admin.getEmail())).thenReturn(false);
        when(adminRepository.save(admin)).thenReturn(1); // Simulate successful save operation

        // Call the service method and validate
        assertDoesNotThrow(() -> adminService.registerAdmin(admin), "RegisterAdmin should not throw exception when email is not in use");
        verify(adminRepository, times(1)).existsByEmail(admin.getEmail());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testRegisterAdminEmailExists() {
        Admin admin = new Admin(1, "admin_name", "admin@example.com", "1234567890", "securePassword");

        // Mock repository behavior
        when(adminRepository.existsByEmail(admin.getEmail())).thenReturn(true);

        // Call the service method and validate
        Exception exception = assertThrows(IllegalArgumentException.class, () -> adminService.registerAdmin(admin), "RegisterAdmin should throw exception when email already exists");
        assertEquals("Admin with this email already exists", exception.getMessage());
        verify(adminRepository, times(1)).existsByEmail(admin.getEmail());
        verify(adminRepository, never()).save(admin);
    }

    @Test
    public void testLoginAdminSuccess() {
        Admin admin = new Admin(1, "admin_name", "admin@example.com", "1234567890", "securePassword");

        // Mock repository behavior
        when(adminRepository.login(admin.getName(), admin.getPassword())).thenReturn(admin);

        // Call the service method and validate
        Admin loggedInAdmin = adminService.loginAdmin("admin_name", "securePassword");
        assertNotNull(loggedInAdmin, "LoginAdmin should return an admin object for valid credentials");
        assertEquals("admin@example.com", loggedInAdmin.getEmail(), "The email should match the mock data");
        verify(adminRepository, times(1)).login(admin.getName(), admin.getPassword());
    }

    @Test
    public void testLoginAdminInvalidCredentials() {
        // Mock repository behavior
        when(adminRepository.login("admin_name", "wrongPassword")).thenReturn(null);

        // Call the service method and validate
        Exception exception = assertThrows(IllegalArgumentException.class, () -> adminService.loginAdmin("admin_name", "wrongPassword"), "LoginAdmin should throw exception for invalid credentials");
        assertEquals("Invalid name or password", exception.getMessage());
        verify(adminRepository, times(1)).login("admin_name", "wrongPassword");
    }
}
