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

import com.janmerhar.rest_api.rest_api.domain.Product;
import com.janmerhar.rest_api.rest_api.dto.CreateProductDto;
import com.janmerhar.rest_api.rest_api.dto.ProductResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProductDuplicateName() {
        CreateProductDto dto = TestDataFactory.createProductDto("prod", "desc");
        when(productRepository.existsByName("prod")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> productService.create(dto));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createProduct() {
        CreateProductDto dto = TestDataFactory.createProductDto("prod", "desc");
        Product saved = TestDataFactory.product(5L, "prod", "desc");
        when(productRepository.existsByName("prod")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponseDto res = productService.create(dto);
        assertEquals(5L, res.getId());
        assertEquals("prod", res.getName());
    }

    @Test
    void getProductMissingId() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getById(1L));
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(
                TestDataFactory.product(1L, "name1", "desc1"),
                TestDataFactory.product(2L, "name2", "desc2")));

        List<ProductResponseDto> res = productService.getAll();
        assertEquals(2, res.size());
        assertEquals("name2", res.get(1).getName());
    }
}
