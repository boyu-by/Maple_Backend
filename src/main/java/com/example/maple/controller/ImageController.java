package com.example.maple.controller;

import com.example.maple.logger.ErrorLogger;
import com.example.maple.model.MindMapData;
import com.example.maple.service.ImageGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mindmap")
public class ImageController {

    @Autowired
    private ImageGeneratorService imageGeneratorService;

    @Autowired
    private ErrorLogger errorLogger;

    @PostMapping("/export/png")
    public ResponseEntity<Map<String, String>> exportAsPNG(@RequestBody MindMapData mindMapData) {
        try {
            String pngDataUrl = imageGeneratorService.generatePNG(mindMapData);
            Map<String, String> response = new HashMap<>();
            response.put("pngDataUrl", pngDataUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            errorLogger.logError("导出PNG失败", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "导出PNG失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}