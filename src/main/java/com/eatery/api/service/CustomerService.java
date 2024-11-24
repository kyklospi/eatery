package com.eatery.api.service;

import com.eatery.api.dto.UpdateUserRequest;
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

    public Customer create(UpdateUserRequest newCustomerRequest) {
        validateCustomer(newCustomerRequest);
        Customer customer = new Customer(
                newCustomerRequest.getFirstName(),
                newCustomerRequest.getLastName(),
                newCustomerRequest.getEmail(),
                newCustomerRequest.getPhoneNumber()
        );
        return customerRepository.save(customer);
    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        customerRepository.delete(customer);
    }

    public Customer get(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer replace(UpdateUserRequest newCustomer, Long id) {
        validateCustomer(newCustomer);
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhoneNumber(newCustomer.getPhoneNumber());
                    return customerRepository.save(customer);
                })
                .orElseThrow(CustomerNotFoundException::new);
    }

    private void validateCustomer(UpdateUserRequest customer) {
        if (customer == null) {
            throw new CustomerBadRequestException("Customer request must not be null.");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new CustomerBadRequestException("Email must not be null or empty.");
        }
        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new CustomerBadRequestException("First Name must not be null or empty.");
        }
        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new CustomerBadRequestException("Last Name must not be null or empty.");
        }
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new CustomerBadRequestException("Phone Number must not be null or empty.");
        }
    }
}