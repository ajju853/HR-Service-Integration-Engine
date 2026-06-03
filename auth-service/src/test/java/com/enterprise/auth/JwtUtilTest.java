package com.enterprise.auth;

import com.enterprise.auth.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void generateAndValidateToken() {
        String token = jwtUtil.generateToken("hr.admin", "HR_MANAGER");
        assertNotNull(token);

        Claims claims = jwtUtil.validateToken(token);
        assertEquals("hr.admin", claims.getSubject());
        assertEquals("HR_MANAGER", claims.get("role"));
    }

    @Test
    void getRole_shouldReturnCorrectRole() {
        String token = jwtUtil.generateToken("admin", "ADMIN");
        assertEquals("ADMIN", jwtUtil.getRole(token));
    }
}
