package com.example.maple.controller;

import com.example.maple.logger.ErrorLogger;
import com.example.maple.model.MindMapData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mindmap")
public class MindMapController {

    @Autowired
    private ErrorLogger errorLogger;

    @GetMapping("/data")
    public ResponseEntity<Map<String, String>> getMindMapData() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "请使用客户端本地存储管理思维导图数据");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/data")
    public ResponseEntity<MindMapData> loadMindMapData(@RequestBody MindMapData mindMapData) {
        return ResponseEntity.ok(mindMapData);
    }

    @PostMapping("/clear")
    public ResponseEntity<Map<String, String>> clearCanvas() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "画布已清空（请在客户端执行清空操作）");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/layout")
    public ResponseEntity<Map<String, String>> getLayoutDirection() {
        Map<String, String> response = new HashMap<>();
        response.put("direction", "left-right");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/layout")
    public ResponseEntity<Map<String, String>> setLayoutDirection(@RequestParam("direction") String direction) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "布局方向已设置为: " + direction);
        response.put("direction", direction);
        return ResponseEntity.ok(response);
    }
}