package com.janmerhar.rest_api.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janmerhar.rest_api.rest_api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}
