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

import com.janmerhar.rest_api.rest_api.domain.Plan;
import com.janmerhar.rest_api.rest_api.domain.Product;
import com.janmerhar.rest_api.rest_api.dto.CreatePlanDto;
import com.janmerhar.rest_api.rest_api.dto.PlanResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.repository.PlanRepository;
import com.janmerhar.rest_api.rest_api.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {
    @Mock
    private PlanRepository planRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PlanService planService;

    @Test
    void createPlanWithoutProduct() {
        CreatePlanDto dto = TestDataFactory.createPlanDto(1L, "plan", "desc");
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> planService.create(dto));
        verify(planRepository, never()).save(any());
    }

    @Test
    void createPlan() {
        Product p = TestDataFactory.product(5L, "Prod", "desc");
        CreatePlanDto dto = TestDataFactory.createPlanDto(5L, "plan", "desc");
        Plan saved = TestDataFactory.plan(10L, p, "plan", "desc");
        when(productRepository.findById(5L)).thenReturn(Optional.of(p));
        when(planRepository.save(any(Plan.class))).thenReturn(saved);

        PlanResponseDto res = planService.create(dto);
        assertEquals(10L, res.getId());
        assertEquals(5L, res.getProductId());
        assertEquals("plan", res.getName());
    }

    @Test
    void getPlanMissingId() {
        when(planRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> planService.getById(1L));
    }

    @Test
    void getPlans() {
        Product p = TestDataFactory.product(1L, "prod", "desc");
        when(planRepository.findAll()).thenReturn(List.of(
                TestDataFactory.plan(1L, p, "prod1", "desc1"),
                TestDataFactory.plan(2L, p, "prod2", "desc2"))
            );

        List<PlanResponseDto> res = planService.getAll();
        assertEquals(2, res.size());
        assertEquals("prod2", res.get(1).getName());
        assertEquals(1L, res.get(0).getProductId());
    }
}
