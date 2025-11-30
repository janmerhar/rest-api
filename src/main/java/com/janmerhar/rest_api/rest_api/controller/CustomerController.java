package com.janmerhar.rest_api.rest_api.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.janmerhar.rest_api.rest_api.dto.CreateCustomerDto;
import com.janmerhar.rest_api.rest_api.dto.CustomerResponseDto;
import com.janmerhar.rest_api.rest_api.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@Valid @RequestBody CreateCustomerDto dto) {
        return customerService.create(dto);
    }

    @GetMapping
    public List<CustomerResponseDto> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerResponseDto getOne(@PathVariable Long id) {
        return customerService.getById(id);
    }
}