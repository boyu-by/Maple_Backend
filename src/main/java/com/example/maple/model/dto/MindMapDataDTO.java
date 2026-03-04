package com.example.maple.model.dto;

import lombok.Data;
import java.util.Map;
import java.util.List;

@Data
public class MindMapDataDTO {
    private Map<String, NodeDTO> nodes;
    private List<String> rootIds;
}