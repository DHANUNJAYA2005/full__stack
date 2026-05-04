package com.campus.events.controller;

import com.campus.events.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/message")
    public Map<String, String> processMessage(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        String reply = chatbotService.processMessage(message);
        return Map.of("reply", reply);
    }
}
