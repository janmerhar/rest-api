package com.janmerhar.rest_api.rest_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.janmerhar.rest_api.rest_api.domain.Customer;
import com.janmerhar.rest_api.rest_api.domain.CustomerType;
import com.janmerhar.rest_api.rest_api.dto.CreateCustomerDto;
import com.janmerhar.rest_api.rest_api.dto.CustomerResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomerDuplicateEmail() {
        CreateCustomerDto dto = TestDataFactory.createCustomerDto("Jan", "a@a.com", CustomerType.INDIVIDUAL);
        when(customerRepository.existsByEmail("a@a.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> customerService.create(dto));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer() {
        CreateCustomerDto dto = TestDataFactory.createCustomerDto("Jan", "a@a.com", CustomerType.INDIVIDUAL);
        Customer saved = TestDataFactory.customer(5L, "Jan", "a@a.com", CustomerType.ORGANIZATION);
        when(customerRepository.existsByEmail("a@a.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(saved);

        CustomerResponseDto response = customerService.create(dto);

        assertEquals(5L, response.getId());
        assertEquals("Jan", response.getName());
        assertEquals("a@a.com", response.getEmail());
        assertEquals(CustomerType.ORGANIZATION, response.getType());
    }

    @Test
    void getCustomerMissingId() {
        when(customerRepository.findById(111L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.getById(111L));
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(
                java.util.List.of(
                        TestDataFactory.customer(1L, "Jan", "a@a.com", CustomerType.INDIVIDUAL),
                        TestDataFactory.customer(2L, "John", "john@example.com", CustomerType.ORGANIZATION)));

        List<CustomerResponseDto> all = customerService.getAll();

        assertEquals(2, all.size());
        assertEquals("Jan", all.get(0).getName());
        assertEquals(CustomerType.ORGANIZATION, all.get(1).getType());
    }
}
