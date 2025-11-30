package com.janmerhar.rest_api.rest_api.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.janmerhar.rest_api.rest_api.dto.CreatePlanDto;
import com.janmerhar.rest_api.rest_api.dto.PlanResponseDto;
import com.janmerhar.rest_api.rest_api.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanResponseDto create(@Valid @RequestBody CreatePlanDto dto) {
        return planService.create(dto);
    }

    @GetMapping
    public List<PlanResponseDto> getAll() {
        return planService.getAll();
    }

    @GetMapping("/{id}")
    public PlanResponseDto getOne(@PathVariable Long id) {
        return planService.getById(id);
    }
}