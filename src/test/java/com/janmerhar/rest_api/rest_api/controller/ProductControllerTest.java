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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.janmerhar.rest_api.rest_api.dto.ProductResponseDto;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.service.ProductService;
import org.mockito.Mockito;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Test
    void createProductSuccessful() throws Exception {
        ProductResponseDto res = TestDataFactory.productResponseDto(1L, "name", "desc");
        when(productService.create(any())).thenReturn(res);

        String payload = "{ \"name\": \"name\", \"description\": \"desc\" }";

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.description").value("desc"));
    }

    @Test
    void createProductInvalidInput() throws Exception {
        String payload = "{ \"name\": \"\", \"description\": \"\" }";

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllProducts() throws Exception {
        when(productService.getAll()).thenReturn(List.of(
                TestDataFactory.productResponseDto(1L, "name1", "desc1"),
                TestDataFactory.productResponseDto(2L, "name2", "desc2"))
            );

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("desc1"))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].description").value("desc2"))
                .andExpect(jsonPath("$[1].name").value("name2"));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        ProductService productService() {
            return Mockito.mock(ProductService.class);
        }
    }
}
