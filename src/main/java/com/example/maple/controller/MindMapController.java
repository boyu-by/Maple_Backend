package com.example.maple.controller;

import com.example.maple.model.dto.EdgeDTO;
import com.example.maple.model.dto.MindMapDTO;
import com.example.maple.model.dto.NodeDTO;
import com.example.maple.service.EdgeService;
import com.example.maple.service.MindMapService;
import com.example.maple.service.NodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mind-maps")
@Tag(name = "MindMap")
public class MindMapController {

    private final MindMapService mindMapService;
    private final NodeService nodeService;
    private final EdgeService edgeService;

    public MindMapController(
            MindMapService mindMapService,
            NodeService nodeService,
            EdgeService edgeService
    ) {
        this.mindMapService = mindMapService;
        this.nodeService = nodeService;
        this.edgeService = edgeService;
    }

    @PostMapping
    @Operation(summary = "Create mind map", description = "Create a new mind map.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public MindMapDTO createMindMap(@Valid @RequestBody MindMapDTO request) {
        return mindMapService.createMindMap(request.getTitle());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get mind map", description = "Get a mind map with its nodes and edges.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public MindMapDetailDTO getMindMap(@PathVariable("id") Long id) {
        MindMapDTO mindMap = mindMapService.getMindMap(id);
        List<NodeDTO> nodes = nodeService.getNodesByMindMapId(id);
        List<EdgeDTO> edges = edgeService.getEdgesByMindMapId(id);
        MindMapDetailDTO response = new MindMapDetailDTO();
        response.setMindMap(mindMap);
        response.setNodes(nodes);
        response.setEdges(edges);
        return response;
    }

    @PutMapping("/{id}/layout")
    @Operation(summary = "Update layout", description = "Update layout direction of a mind map.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public void updateLayoutDirection(@PathVariable("id") Long id, @Valid @RequestBody MindMapDTO request) {
        mindMapService.updateLayoutDirection(id, request.getLayoutDirection());
    }

    @DeleteMapping("/{id}/clear")
    @Operation(summary = "Clear mind map", description = "Remove all nodes and edges from a mind map.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public void clearMindMap(@PathVariable("id") Long id) {
        mindMapService.clearMindMap(id);
    }

    @Data
    private static class MindMapDetailDTO {
        private MindMapDTO mindMap;
        private List<NodeDTO> nodes;
        private List<EdgeDTO> edges;
    }
}
