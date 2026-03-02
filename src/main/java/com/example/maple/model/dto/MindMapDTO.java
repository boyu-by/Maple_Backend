package com.example.maple.model.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MindMapDTO {
    private Long id;
    @NotBlank
    private String title;
    private String layoutDirection;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
