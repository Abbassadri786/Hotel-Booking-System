package com.hbs.HBS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hbs.HBS.model.Customer;
import com.hbs.HBS.repository.CustomerRepository;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1, "john_doe", "john@example.com", "password123", "1234567890", "Male", Date.valueOf("1990-01-01"), "2023-10-01");
    }

    @Test
    public void testAddCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(1);

        customerService.addCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testLoginCustomer() {
        when(customerRepository.login(anyString(), anyString())).thenReturn(customer);

        Customer result = customerService.loginCustomer("john_doe", "password123");
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(customerRepository, times(1)).login(anyString(), anyString());
    }

    @Test
    public void testGetCustomerByUsername() {
        when(customerRepository.findByUsername(anyString())).thenReturn(customer);

        Optional<Customer> result = customerService.getCustomerByUsername("john_doe");
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(customerRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(anyInt())).thenReturn(customer);

        Optional<Customer> result = customerService.getCustomerById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(customerRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testUpdateCustomer() {
        when(customerRepository.update(any(Customer.class))).thenReturn(1);

        int result = customerService.updateCustomer(customer);
        assertEquals(1, result);
        verify(customerRepository, times(1)).update(any(Customer.class));
    }

    @Test
    public void testDeleteById() {
        when(customerRepository.delete(anyInt())).thenReturn(1);

        int result = customerService.deleteById(1);
        assertEquals(1, result);
        verify(customerRepository, times(1)).delete(anyInt());
    }
}
