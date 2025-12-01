package com.janmerhar.rest_api.rest_api.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.janmerhar.rest_api.rest_api.dto.CreateProductDto;
import com.janmerhar.rest_api.rest_api.dto.ProductResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.repository.PlanRepository;
import com.janmerhar.rest_api.rest_api.repository.ProductRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@ActiveProfiles("test")
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PlanRepository planRepository;

    @BeforeEach
    void clean() {
        planRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void createProduct() {
        CreateProductDto dto = new CreateProductDto();
        dto.setName("prod");
        dto.setDescription("desc");

        ProductResponseDto created = productService.create(dto);
        assertNotNull(created.getId());

        ProductResponseDto fetched = productService.getById(created.getId());
        assertEquals("prod", fetched.getName());
        assertEquals("desc", fetched.getDescription());
        assertNotNull(fetched.getCreatedAt());
    }

    @Test
    void createProductDuplicateName() {
        CreateProductDto dto = new CreateProductDto();
        dto.setName("duplicate prod");
        dto.setDescription("desc");

        productService.create(dto);
        assertThrows(IllegalArgumentException.class, () -> productService.create(dto));
    }

    @Test
    void getProductMissingId() {
        assertThrows(NotFoundException.class, () -> productService.getById(999L));
    }
}
