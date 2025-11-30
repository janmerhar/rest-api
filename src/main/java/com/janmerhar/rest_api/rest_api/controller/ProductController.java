package com.janmerhar.rest_api.rest_api.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.janmerhar.rest_api.rest_api.dto.CreateProductDto;
import com.janmerhar.rest_api.rest_api.dto.ProductResponseDto;
import com.janmerhar.rest_api.rest_api.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@Valid @RequestBody CreateProductDto dto) {
        return productService.create(dto);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getOne(@PathVariable Long id) {
        return productService.getById(id);
    }
}