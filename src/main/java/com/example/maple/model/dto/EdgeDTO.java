package com.example.maple.model.dto;

import lombok.Data;

@Data
public class EdgeDTO {
    private Long id;
    private Long mindMapId;
    private Long sourceNodeId;
    private Long targetNodeId;
}