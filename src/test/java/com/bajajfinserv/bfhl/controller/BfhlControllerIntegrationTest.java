package com.bajajfinserv.bfhl.controller;

import com.bajajfinserv.bfhl.config.UserProfileProperties;
import com.bajajfinserv.bfhl.exception.ApiExceptionHandler;
import com.bajajfinserv.bfhl.service.DefaultBfhlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BfhlControllerIntegrationTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        UserProfileProperties profile = new UserProfileProperties(
                "Anuj Kumar", "01042005", "anuj1699.be23@chitkara.edu.in", "2310991699");
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new BfhlController(new DefaultBfhlService(profile)))
                .setControllerAdvice(new ApiExceptionHandler(profile))
                .setValidator(validator)
                .build();
    }

    @Test
    void acceptsTheRequiredPostRequest() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\":[\"2\",\"a\",\"y\",\"4\",\"&\",\"-\",\"*\",\"5\",\"92\",\"b\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers[0]").value("5"))
                .andExpect(jsonPath("$.even_numbers[2]").value("92"))
                .andExpect(jsonPath("$.alphabets[2]").value("B"))
                .andExpect(jsonPath("$.special_characters[1]").value("-"))
                .andExpect(jsonPath("$.sum").value("103"))
                .andExpect(jsonPath("$.concat_string").value("ByA"));
    }

    @Test
    void returnsAGracefulResponseForAnInvalidRequest() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false))
                .andExpect(jsonPath("$.user_id").exists())
                .andExpect(jsonPath("$.error").value("data: data is required"));
    }
}
