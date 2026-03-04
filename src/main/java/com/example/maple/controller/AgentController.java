package com.example.maple.controller;

import com.example.maple.logger.ErrorLogger;
import com.example.maple.service.DifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private ErrorLogger errorLogger;

    @Autowired
    private DifyService difyService;

    @PostMapping("/suggest")
    public ResponseEntity<?> getMindMapSuggestion(@RequestParam("topic") String topic) {
        try {
            String response = difyService.getMindMapSuggestion(topic);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            errorLogger.logError("获取思维导图建议失败", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "获取思维导图建议失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}