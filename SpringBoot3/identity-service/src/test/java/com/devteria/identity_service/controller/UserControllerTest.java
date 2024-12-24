package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Mock
    UserService userService;

    UserCreationRequest request;
    UserResponse response;
    LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2000, 5, 8);
        request = UserCreationRequest.builder()
                .username("root")
                .firstName("nguyen")
                .lastName("nguyen")
                .password("12345678")
                .dob(dob)
                .build();
        response = UserResponse.builder()
                .id("dgfgejfgewjgfwe")
                .username("root")
                .firstName("nguyen")
                .lastName("nguyen")
                .dob(dob)
                .build();
    }

    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"}, authorities = {"ADMIN_CREATE_DATA"})
    void createUser() throws Exception {
        when(userService.createRequest(any())).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(201))
                .andReturn();
    }
}
