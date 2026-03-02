package com.example.maple.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NodeDTO {
    private Long id;
    @NotNull
    private Long mindMapId;
    private Long parentNodeId;
    @NotBlank
    private String content;
    @NotNull
    private Double x;
    @NotNull
    private Double y;
    private Boolean collapsed;
}
