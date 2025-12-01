package com.janmerhar.rest_api.rest_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.janmerhar.rest_api.rest_api.domain.CustomerType;
import com.janmerhar.rest_api.rest_api.dto.CustomerResponseDto;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.service.CustomerService;
import org.mockito.Mockito;

@WebMvcTest(CustomerController.class)
@Import(CustomerControllerTest.MockConfig.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomerSuccessful() throws Exception {
        CustomerResponseDto response = TestDataFactory.customerResponseDto(10L, "Jan", "a@a.com", CustomerType.INDIVIDUAL);
        when(customerService.create(any())).thenReturn(response);

        String payload = "{\"name\": \"name\",\"email\": \"a@a.com\", \"type\": \"INDIVIDUAL\"}";

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.email").value("a@a.com"))
                .andExpect(jsonPath("$.type").value("INDIVIDUAL"));
    }

    @Test
    void createCustomerInvalidInput() throws Exception {
        String payload = "{\"name\": \"\",\"email\": \"email\", \"type\": null}";

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerResponseDto> customers = List.of(
                TestDataFactory.customerResponseDto(1L, "name1", "a@a.com", CustomerType.INDIVIDUAL),
                TestDataFactory.customerResponseDto(2L, "name2", "b@b.com", CustomerType.ORGANIZATION)
                );
        when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].email").value("a@a.com"))
                .andExpect(jsonPath("$[0].type").value("INDIVIDUAL"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].email").value("b@b.com"))
                .andExpect(jsonPath("$[1].type").value("ORGANIZATION"));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        CustomerService customerService() {
            return Mockito.mock(CustomerService.class);
        }
    }
}
