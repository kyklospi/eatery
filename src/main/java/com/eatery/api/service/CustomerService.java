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
    CustomerRepository userRepository;

    public List<Customer> getAll() {
        return userRepository.findAll();
    }

    public Customer create(Customer newUser) {
        validateUser(newUser);
        return userRepository.save(newUser);
    }

    public void deleteById(Long id) {
        Customer user = userRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        userRepository.delete(user);
    }

    public Customer findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private void validateUser(Customer user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new CustomerBadRequestException("Email cannot be null or empty.");
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new CustomerBadRequestException("First Name cannot be null or empty.");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new CustomerBadRequestException("Last Name cannot be null or empty.");
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new CustomerBadRequestException("Phone Number cannot be null or empty.");
        }

    }



}