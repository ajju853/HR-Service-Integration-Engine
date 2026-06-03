package com.enterprise.notification;

import com.enterprise.notification.controller.NotificationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @InjectMocks
    private NotificationController controller;

    @Test
    void sendEmail_shouldReturnSent() {
        Map<String, Object> input = new HashMap<>();
        input.put("to", "test@example.com");
        input.put("subject", "Test");
        input.put("body", "Hello");

        ResponseEntity<Map<String, Object>> response = controller.sendEmail(input);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("sent", response.getBody().get("status"));
        assertEquals("email", response.getBody().get("channel"));
    }

    @Test
    void sendSms_shouldReturnSent() {
        Map<String, Object> input = new HashMap<>();
        input.put("to", "+911234567890");

        ResponseEntity<Map<String, Object>> response = controller.sendSms(input);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("sent", response.getBody().get("status"));
        assertEquals("sms", response.getBody().get("channel"));
    }
}
