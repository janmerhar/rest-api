package com.janmerhar.rest_api.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janmerhar.rest_api.rest_api.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}