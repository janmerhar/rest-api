package com.janmerhar.rest_api.rest_api.service;

import org.springframework.stereotype.Service;

import com.janmerhar.rest_api.rest_api.domain.Customer;
import com.janmerhar.rest_api.rest_api.dto.CreateCustomerDto;
import com.janmerhar.rest_api.rest_api.dto.CustomerResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDto create(CreateCustomerDto dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email in use");
        }

        Customer c = new Customer();
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setType(dto.getType());

        Customer saved = customerRepository.save(c);
        return toResponse(saved);
    }

    public CustomerResponseDto getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return toResponse(customer);
    }

    public List<CustomerResponseDto> getAll() {
        return customerRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private CustomerResponseDto toResponse(Customer customer) {
        CustomerResponseDto res = new CustomerResponseDto();

        res.setId(customer.getId());
        res.setName(customer.getName());
        res.setEmail(customer.getEmail());
        res.setType(customer.getType());
        res.setCreatedAt(customer.getCreatedAt());

        return res;
    }
}