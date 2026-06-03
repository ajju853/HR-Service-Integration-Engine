package com.enterprise.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class NotificationController {

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody Map<String, Object> payload) {
        String to = (String) payload.getOrDefault("to", "unknown");
        String subject = (String) payload.getOrDefault("subject", "No Subject");
        Map<String, Object> result = new LinkedHashMap<>(payload);
        result.put("status", "sent");
        result.put("channel", "email");
        result.put("timestamp", LocalDateTime.now().toString());
        result.put("message", "Email sent to " + to + " with subject: " + subject);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send-sms")
    public ResponseEntity<Map<String, Object>> sendSms(@RequestBody Map<String, Object> payload) {
        String to = (String) payload.getOrDefault("to", "unknown");
        Map<String, Object> result = new LinkedHashMap<>(payload);
        result.put("status", "sent");
        result.put("channel", "sms");
        result.put("timestamp", LocalDateTime.now().toString());
        result.put("message", "SMS sent to " + to);
        return ResponseEntity.ok(result);
    }
}
