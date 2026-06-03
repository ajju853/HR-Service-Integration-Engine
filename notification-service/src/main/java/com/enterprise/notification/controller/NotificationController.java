package com.enterprise.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class NotificationController {

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody Map<String, Object> payload) {
        String to = (String) payload.getOrDefault("to", "unknown");
        String subject = (String) payload.getOrDefault("subject", "No Subject");

        payload.put("status", "sent");
        payload.put("channel", "email");
        payload.put("timestamp", LocalDateTime.now().toString());
        payload.put("message", "Email sent to " + to + " with subject: " + subject);
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/send-sms")
    public ResponseEntity<Map<String, Object>> sendSms(@RequestBody Map<String, Object> payload) {
        String to = (String) payload.getOrDefault("to", "unknown");
        payload.put("status", "sent");
        payload.put("channel", "sms");
        payload.put("timestamp", LocalDateTime.now().toString());
        payload.put("message", "SMS sent to " + to);
        return ResponseEntity.ok(payload);
    }
}
