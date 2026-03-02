package com.example.maple.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MindMapDTO {
    private Long id;
    private String title;
    private String layoutDirection;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}