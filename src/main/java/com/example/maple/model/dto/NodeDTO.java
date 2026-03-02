package com.example.maple.model.dto;

import lombok.Data;

@Data
public class NodeDTO {
    private Long id;
    private Long mindMapId;
    private Long parentNodeId;
    private String content;
    private Double x;
    private Double y;
    private Boolean collapsed;
}