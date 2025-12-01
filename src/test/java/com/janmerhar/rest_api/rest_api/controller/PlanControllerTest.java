package com.janmerhar.rest_api.rest_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.janmerhar.rest_api.rest_api.dto.PlanResponseDto;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.service.PlanService;
import org.mockito.Mockito;

@WebMvcTest(PlanController.class)
@Import(PlanControllerTest.MockConfig.class)
class PlanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanService planService;

    @Test
    void createPlanSuccessful() throws Exception {
        PlanResponseDto res = TestDataFactory.planResponseDto(1L, 5L, "name", "desc");
        when(planService.create(any())).thenReturn(res);

        String payload = "{ \"productId\": 5, \"name\": \"name\", \"description\": \"desc\", \"price\": 10 }";

        mockMvc.perform(post("/api/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productId").value(5L))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.price").value(10L));
    }

    @Test
    void createPlanInvalidInput() throws Exception {
        String payload = "{ \"productId\": null, \"name\": \"\", \"description\": \"\", \"price\": -10 }";

        mockMvc.perform(post("/api/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPlans() throws Exception {
        when(planService.getAll()).thenReturn(List.of(
                TestDataFactory.planResponseDto(1L, 5L, "name1", "desc1"),
                TestDataFactory.planResponseDto(2L, 5L, "name2", "desc2"))
            );

        mockMvc.perform(get("/api/plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].productId").value(5L))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].description").value("desc1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].productId").value(5L))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].description").value("desc2"));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        PlanService planService() {
            return Mockito.mock(PlanService.class);
        }
    }
}
