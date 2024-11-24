package com.eatery.api.service;

import com.eatery.exception.CustomerBadRequestException;
import com.eatery.exception.CustomerNotFoundException;
import com.eatery.entity.Customer;
import com.eatery.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer create(Customer newUser) {
        validateCustomer(newUser);
        return customerRepository.save(newUser);
    }

    public void deleteById(Long id) {
        Customer user = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(user);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private void validateCustomer(Customer customer) {
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new CustomerBadRequestException("Email cannot be null or empty.");
        }
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
            throw new CustomerBadRequestException("First Name cannot be null or empty.");
        }
        if (customer.getLastName() == null || customer.getLastName().isEmpty()) {
            throw new CustomerBadRequestException("Last Name cannot be null or empty.");
        }
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isEmpty()) {
            throw new CustomerBadRequestException("Phone Number cannot be null or empty.");
        }

    }



}