package com.example.maple.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class NodeDTO {
    private String id;
    @NotBlank
    private String text;
    @NotNull
    private Double x;
    @NotNull
    private Double y;
    private Double width;
    private Double height;
    private String parentId;
    private List<String> children;
    private Boolean collapsed;
}
