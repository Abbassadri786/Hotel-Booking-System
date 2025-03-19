package com.hbs.HBS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hbs.HBS.model.Customer;
import com.hbs.HBS.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Add a new customer
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    // Customer login
    public Customer loginCustomer(String username, String password) {
        return customerRepository.login(username, password);
    }
    
    // Get customer by Username
    public Optional<Customer> getCustomerByUsername(String username) {
        return Optional.ofNullable(customerRepository.findByUsername(username));
    }

    // Get customer by Id
    public Optional<Customer> getCustomerById(int id) {
        return Optional.ofNullable(customerRepository.findById(id));
    }

    // Update customer
    public int updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    // Delete customer by Id
    public int deleteById(int id) {
        return customerRepository.delete(id);
    }
}
