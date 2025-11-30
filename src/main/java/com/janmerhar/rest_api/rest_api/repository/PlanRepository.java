package com.janmerhar.rest_api.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janmerhar.rest_api.rest_api.domain.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}