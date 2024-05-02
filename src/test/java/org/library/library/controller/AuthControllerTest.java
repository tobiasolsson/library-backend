package org.library.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.library.library.exception.DuplicateEmailException;
import org.library.library.exception.InvalidEmailException;
import org.library.library.exception.InvalidPasswordException;
import org.library.library.model.LoginRequest;
import org.library.library.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
    @Autowired
    private MockMvc api;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void userCanRegister() throws Exception {
        String email = "test@test.com";
        String password = "123456";
        var request = LoginRequest.builder()
                                  .email(email)
                                  .password(password)
                                  .build();
        var requestBody = objectMapper.writeValueAsString(request);

        when(authService.registerNewUser(email, password)).thenReturn("New user registered successfully with id: 123");

        api.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
           .andExpect(status().isCreated());
    }

    @Test
    void userCannotRegister_emailExists() throws Exception {
        String email = "existing@test.com";
        String password = "123456";
        var request = LoginRequest.builder()
                                  .email(email)
                                  .password(password)
                                  .build();
        var requestBody = objectMapper.writeValueAsString(request);

        when(authService.registerNewUser(email, password)).thenThrow(new DuplicateEmailException("Email already exists"));

        api.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
           .andExpect(status().isBadRequest());
    }

    @Test
    void userCannotRegister_passwordShort() throws Exception {
        String email = "test@test.com";
        String password = "123";
        var request = LoginRequest.builder()
                                  .email(email)
                                  .password(password)
                                  .build();
        var requestBody = objectMapper.writeValueAsString(request);

        when(authService.registerNewUser(email, password)).thenThrow(new InvalidPasswordException("3"));

        api.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
           .andExpect(status().isBadRequest());
    }

    @Test
    void userCannotRegister_emptyEmail() throws Exception {
        String email = "";
        String password = "123456";
        var request = LoginRequest.builder()
                                  .email(email)
                                  .password(password)
                                  .build();
        var requestBody = objectMapper.writeValueAsString(request);

        when(authService.registerNewUser(email, password)).thenThrow(new InvalidEmailException(""));

        api.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
           .andExpect(status().isBadRequest());
    }


    // login tests

    // test successful login
    // test no email login
    // test wrong password login

}