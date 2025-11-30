package com.janmerhar.rest_api.rest_api.service;

import org.springframework.stereotype.Service;

import com.janmerhar.rest_api.rest_api.domain.Product;
import com.janmerhar.rest_api.rest_api.dto.CreateProductDto;
import com.janmerhar.rest_api.rest_api.dto.ProductResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    public ProductResponseDto create(CreateProductDto dto) {
        if (productRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Product name duplicate");
        }

        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());

        Product saved = productRepository.save(p);
        return toResponse(saved);
    }

    public ProductResponseDto getById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return toResponse(p);
    }

    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponseDto toResponse(Product product) {
        ProductResponseDto res = new ProductResponseDto();

        res.setId(product.getId());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setCreatedAt(product.getCreatedAt());

        return res;
    }
}