package com.janmerhar.rest_api.rest_api.service;
import org.springframework.stereotype.Service;

import com.janmerhar.rest_api.rest_api.domain.Plan;
import com.janmerhar.rest_api.rest_api.domain.Product;
import com.janmerhar.rest_api.rest_api.dto.CreatePlanDto;
import com.janmerhar.rest_api.rest_api.dto.PlanResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.repository.PlanRepository;
import com.janmerhar.rest_api.rest_api.repository.ProductRepository;

import java.util.List;

@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final ProductRepository productRepository;

    public PlanService(PlanRepository planRepository, ProductRepository productRepository) {
        this.planRepository = planRepository;
        this.productRepository = productRepository;
    }

    public PlanResponseDto create(CreatePlanDto dto) {
        Product pr = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Plan pl = new Plan();
        pl.setProduct(pr);
        pl.setName(dto.getName());
        pl.setDescription(dto.getDescription());
        pl.setPrice(dto.getPrice());

        Plan saved = planRepository.save(pl);
        return toResponse(saved);
    }

    public PlanResponseDto getById(Long id) {
        Plan p = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan not found"));
        
        return toResponse(p);
    }

    public List<PlanResponseDto> getAll() {
        return planRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private PlanResponseDto toResponse(Plan plan) {
        PlanResponseDto res = new PlanResponseDto();
        
        res.setId(plan.getId());
        res.setProductId(plan.getProduct().getId());
        res.setName(plan.getName());
        res.setDescription(plan.getDescription());
        res.setPrice(plan.getPrice());
        res.setCreatedAt(plan.getCreatedAt());

        return res;
    }
}