package com.example.Habr_Parser_Project;

import com.example.Habr_Parser_Project.controller.SecurityController;
import com.example.Habr_Parser_Project.dto.SignInRequest;
import com.example.Habr_Parser_Project.dto.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityController securityController;

    @Test
    void contextLoads() throws Exception{
        assertThat(securityController).isNotNull();
    }
    @Test
    void shouldReturnUserRegisteredSuccess() throws Exception{
        SignUpRequest signUpRequest = new SignUpRequest("user1", "user12@mail.ru", "user");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(signUpRequest);
        this.mockMvc.perform(post("http://localhost:8080/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User registered success"));
    }
    @Test
    void shouldReturnJWTToken() throws Exception{
        SignInRequest signInRequest = new SignInRequest("user1", "user");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(signInRequest);
        this.mockMvc.perform(post("http://localhost:8080/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }
}
