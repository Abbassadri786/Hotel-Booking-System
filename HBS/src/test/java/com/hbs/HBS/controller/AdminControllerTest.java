package com.hbs.HBS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbs.HBS.dto.AdminLoginRequest;
import com.hbs.HBS.model.Admin;
import com.hbs.HBS.model.AdminRegistrationRequest;
import com.hbs.HBS.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegisterAdminSuccess() throws Exception {
        Admin admin = new Admin(1, "admin_name", "admin@example.com", "1234567890", "securePassword");
        AdminRegistrationRequest request = new AdminRegistrationRequest();
        request.setAccessCode("admin@1234");
        request.setAdmin(admin);

        // Simulate successful service method call
        Mockito.doNothing().when(adminService).registerAdmin(any(Admin.class));

        mockMvc.perform(post("/api/admins/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin registered successfully"));
    }

    @Test
    public void testRegisterAdminInvalidAccessCode() throws Exception {
        Admin admin = new Admin(1, "admin_name", "admin@example.com", "1234567890", "securePassword");
        AdminRegistrationRequest request = new AdminRegistrationRequest();
        request.setAccessCode("wrongCode");
        request.setAdmin(admin);

        mockMvc.perform(post("/api/admins/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Invalid access code"));
    }

    @Test
    public void testRegisterAdminBadRequest() throws Exception {
        AdminRegistrationRequest invalidRequest = new AdminRegistrationRequest();

        mockMvc.perform(post("/api/admins/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request"));
    }

    @Test
    public void testLoginAdminSuccess() throws Exception {
        Admin admin = new Admin(1, "admin_name", "admin@example.com", "1234567890", "securePassword");
        AdminLoginRequest loginRequest = new AdminLoginRequest();
        loginRequest.setName("admin_name");
        loginRequest.setPassword("securePassword");

        when(adminService.loginAdmin("admin_name", "securePassword")).thenReturn(admin);

        mockMvc.perform(post("/api/admins/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin login successful"));
    }

    @Test
    public void testLoginAdminInvalidCredentials() throws Exception {
        AdminLoginRequest loginRequest = new AdminLoginRequest();
        loginRequest.setName("wrong_name");
        loginRequest.setPassword("wrongPassword");

        when(adminService.loginAdmin("wrong_name", "wrongPassword")).thenReturn(null);

        mockMvc.perform(post("/api/admins/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));
    }

    @Test
    public void testLoginAdminBadRequest() throws Exception {
        AdminLoginRequest invalidRequest = new AdminLoginRequest();

        mockMvc.perform(post("/api/admins/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request"));
    }
}
