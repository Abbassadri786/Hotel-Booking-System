package com.hbs.HBS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbs.HBS.dto.LoginRequest;
import com.hbs.HBS.model.Customer;
import com.hbs.HBS.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> fetchCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    // Fetch customer by username with a unique path
    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<Customer>> fetchCustomerByUsername(@PathVariable String username) {
        Optional<Customer> customer = customerService.getCustomerByUsername(username);
        return ResponseEntity.ok(customer);
    }

    // Fetch customer by ID with a unique path
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Customer>> fetchCustomerById(@PathVariable int id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@Valid @RequestBody LoginRequest loginRequest) {
        Customer loggedInCustomer = customerService.loginCustomer(loginRequest.getUsername(), loginRequest.getPassword());
        if (loggedInCustomer != null) {
            return ResponseEntity.ok("Customer login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping
    public String addCustomer(@Valid @RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return "Customer added successfully";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        try {
            int rowsAffected = customerService.deleteById(id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Customer Deleted Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting Customer");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id,@Valid @RequestBody Customer customer) {
        try {
            customer.setId(id);
            int rowsAffected = customerService.updateCustomer(customer);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Customer updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating customer");
        }
    }
}
