package com.enterprise.auth;

import com.enterprise.auth.controller.AuthController;
import com.enterprise.auth.model.LoginRequest;
import com.enterprise.auth.model.LoginResponse;
import com.enterprise.auth.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController controller;

    @Test
    void login_shouldReturn401_forInvalidCredentials() {
        LoginRequest request = new LoginRequest();
        request.setUsername("wrong");
        request.setPassword("wrong");
        ResponseEntity<?> response = controller.login(request);
        assertEquals(401, response.getStatusCodeValue());
    }
}
