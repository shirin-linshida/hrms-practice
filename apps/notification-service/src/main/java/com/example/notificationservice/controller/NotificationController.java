package com.example.notificationservice.controller;

import com.example.notificationservice.model.EmailRequest;
import com.example.notificationservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public void sendNotification(@RequestBody EmailRequest emailRequest) {
        emailService.sendSimpleMessage(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Notification service is up and running";
    }
}


