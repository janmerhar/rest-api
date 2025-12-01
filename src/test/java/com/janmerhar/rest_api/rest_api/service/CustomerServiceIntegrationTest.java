package com.janmerhar.rest_api.rest_api.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.janmerhar.rest_api.rest_api.domain.CustomerType;
import com.janmerhar.rest_api.rest_api.dto.CreateCustomerDto;
import com.janmerhar.rest_api.rest_api.dto.CustomerResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@ActiveProfiles("test")
class CustomerServiceIntegrationTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomer() {
        CreateCustomerDto dto = new CreateCustomerDto();
        dto.setName("name");
        dto.setEmail("a@a.com");
        dto.setType(CustomerType.INDIVIDUAL);

        CustomerResponseDto created = customerService.create(dto);

        assertNotNull(created.getId());

        CustomerResponseDto fetched = customerService.getById(created.getId());
        assertEquals("name", fetched.getName());
        assertEquals("a@a.com", fetched.getEmail());
        assertEquals(CustomerType.INDIVIDUAL, fetched.getType());
        assertNotNull(fetched.getCreatedAt());
    }

    @Test
    void createCustomerDuplicateEmail() {
        CreateCustomerDto c1 = new CreateCustomerDto();
        c1.setName("name1");
        c1.setEmail("b@b.com");
        c1.setType(CustomerType.INDIVIDUAL);

        CreateCustomerDto c2 = new CreateCustomerDto();
        c2.setName("name2");
        c2.setEmail("b@b.com");
        c2.setType(CustomerType.ORGANIZATION);

        customerService.create(c1);
        assertThrows(IllegalArgumentException.class, () -> customerService.create(c2));
    }

    @Test
    void getCustomerMissingId() {
        assertThrows(NotFoundException.class, () -> customerService.getById(111L));
    }
}
