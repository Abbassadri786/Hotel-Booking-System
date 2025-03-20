package com.hbs.HBS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.hbs.HBS.dto.LoginRequest;
import com.hbs.HBS.model.Customer;
import com.hbs.HBS.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1, "john_doe", "john@example.com", "password123", "1234567890", "Male", Date.valueOf("1990-01-01"), "2023-10-01");
    }

    @Test
    public void testFetchCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer));

        mockMvc.perform(get("/api/customers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].username").value("john_doe"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void testFetchCustomerByUsername() throws Exception {
        when(customerService.getCustomerByUsername(anyString())).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/username/john_doe"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.username").value("john_doe"));

        verify(customerService, times(1)).getCustomerByUsername(anyString());
    }

    @Test
    public void testFetchCustomerById() throws Exception {
        when(customerService.getCustomerById(anyInt())).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/id/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1));

        verify(customerService, times(1)).getCustomerById(anyInt());
    }

    @Test
    public void testLoginCustomerSuccessful() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("john_doe");
        loginRequest.setPassword("password123");
        when(customerService.loginCustomer("john_doe", "password123")).thenReturn(customer);

        mockMvc.perform(post("/api/customers/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john_doe\", \"password\":\"password123\"}"))
            .andExpect(status().isOk())
            .andExpect(content().string("Customer login successful"));

        verify(customerService, times(1)).loginCustomer("john_doe", "password123");
    }

    @Test
    public void testLoginCustomerInvalid() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("wrong_user");
        loginRequest.setPassword("wrong_pass");
        when(customerService.loginCustomer("wrong_user", "wrong_pass")).thenThrow(new IllegalArgumentException("Invalid username or password"));

        mockMvc.perform(post("/api/customers/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"wrong_user\", \"password\":\"wrong_pass\"}"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Invalid username or password"));

        verify(customerService, times(1)).loginCustomer("wrong_user", "wrong_pass");
    }

    @Test
    public void testAddCustomer() throws Exception {
        String jsonCustomer = "{ \"name\":\"admin_name\",\"email\":\"admin@example.com\",\"mobile\":\"1234567890\",\"password\":\"securePassword\" }";

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCustomer))
            .andExpect(status().isOk())
            .andExpect(content().string("Customer added successfully"));

        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        when(customerService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/api/customers/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Customer Deleted Successfully"));

        verify(customerService, times(1)).deleteById(anyInt());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        String jsonCustomer = "{ \"id\":1, \"name\":\"newName\", \"email\":\"newEmail@example.com\", \"mobile\":\"0987654321\", \"password\":\"newSecurePassword\" }";

        when(customerService.updateCustomer(any(Customer.class))).thenReturn(1);

        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCustomer))
            .andExpect(status().isOk())
            .andExpect(content().string("Customer updated successfully"));

        verify(customerService, times(1)).updateCustomer(any(Customer.class));
    }
}
