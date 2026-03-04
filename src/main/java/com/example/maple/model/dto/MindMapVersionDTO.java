package com.example.maple.model.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MindMapVersionDTO {

    private Long id;
    private Long mindMapId;
    private Integer version;
    private String title;
    private String description;
    private String layoutDirection;
    private LocalDateTime createdAt;
    private String createdBy;

    // 可选字段，用于创建版本时提供
    private MindMapDataDTO mapData;
}