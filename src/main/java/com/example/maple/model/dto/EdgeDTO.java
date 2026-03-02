package com.example.maple.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EdgeDTO {
    private Long id;
    @NotNull
    private Long mindMapId;
    @NotNull
    private Long sourceNodeId;
    @NotNull
    private Long targetNodeId;
}
