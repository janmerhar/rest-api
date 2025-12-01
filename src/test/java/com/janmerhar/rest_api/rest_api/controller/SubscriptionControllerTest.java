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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.janmerhar.rest_api.rest_api.dto.SubscriptionResponseDto;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.service.SubscriptionService;
                         
@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    void createSubscriptionSuccessful() throws Exception {
        SubscriptionResponseDto res = TestDataFactory.subscriptionResponseDto(1L, 2L, 3L);
        when(subscriptionService.create(any())).thenReturn(res);

        String payload = "{\"customerId\": 2,\"planId\": 3}";

        mockMvc.perform(post("/api/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(2L))
                .andExpect(jsonPath("$.planId").value(3L));
    }

    @Test
    void createSubscriptionInvalidInput() throws Exception {
        String payload = "{\"customerId\": null,\"planId\": null}";

        mockMvc.perform(post("/api/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllSubscriptions() throws Exception {
        when(subscriptionService.getAll()).thenReturn(List.of(
                TestDataFactory.subscriptionResponseDto(1L, 2L, 3L),
                TestDataFactory.subscriptionResponseDto(2L, 2L, 3L))
            );

        mockMvc.perform(get("/api/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].customerId").value(2L))
                .andExpect(jsonPath("$[0].planId").value(3L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].customerId").value(2L))
                .andExpect(jsonPath("$[1].planId").value(3L));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public SubscriptionService subscriptionService() {
            return org.mockito.Mockito.mock(SubscriptionService.class);
        }
    }
}
