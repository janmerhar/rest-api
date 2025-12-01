package com.janmerhar.rest_api.rest_api.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.janmerhar.rest_api.rest_api.domain.Product;
import com.janmerhar.rest_api.rest_api.dto.CreatePlanDto;
import com.janmerhar.rest_api.rest_api.dto.PlanResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.repository.PlanRepository;
import com.janmerhar.rest_api.rest_api.repository.ProductRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@ActiveProfiles("test")
class PlanServiceIntegrationTest {
    @Autowired
    private PlanService planService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PlanRepository planRepository;

    @BeforeEach
    void setUp() {
        planRepository.deleteAll();
        productRepository.deleteAll();
    }

    private Long createProduct() {
        Product p = new Product();

        p.setName("name");
        p.setDescription("desc");

        return productRepository.save(p).getId();
    }

    @Test
    void createPlan() {
        Long productId = createProduct();

        CreatePlanDto dto = new CreatePlanDto();
        dto.setProductId(productId);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setPrice(java.math.BigDecimal.TEN);

        PlanResponseDto created = planService.create(dto);

        assertNotNull(created.getId());
        assertEquals(productId, created.getProductId());
        assertEquals("name", planService.getById(created.getId()).getName());
    }

    @Test
    void createPlanWithoutProduct() {
        CreatePlanDto dto = new CreatePlanDto();
        dto.setProductId(111L);
        dto.setName("plan");
        dto.setDescription("desc");
        dto.setPrice(java.math.BigDecimal.ONE);

        assertThrows(NotFoundException.class, () -> planService.create(dto));
    }

    @Test
    void getAllPlans() {
        Long productId = createProduct();
        
        CreatePlanDto dto = new CreatePlanDto();
        dto.setProductId(productId);
        dto.setName("plan");
        dto.setDescription("desc");
        dto.setPrice(java.math.BigDecimal.valueOf(25));

        planService.create(dto);

        List<PlanResponseDto> all = planService.getAll();
        assertFalse(all.isEmpty());
        assertTrue(all.stream().anyMatch(p -> p.getName().equals("plan")));
    }
}
