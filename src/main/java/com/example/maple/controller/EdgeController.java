package com.example.maple.controller;

import com.example.maple.model.dto.EdgeDTO;
import com.example.maple.service.EdgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edges")
@Tag(name = "Edge")
public class EdgeController {

    private final EdgeService edgeService;

    public EdgeController(EdgeService edgeService) {
        this.edgeService = edgeService;
    }

    @PostMapping
    @Operation(summary = "Create edge", description = "Create a connection between two nodes.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public EdgeDTO createEdge(@Valid @RequestBody EdgeDTO request) {
        return edgeService.createEdge(
                request.getMindMapId(),
                request.getSourceNodeId(),
                request.getTargetNodeId()
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete edge", description = "Delete a connection by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public void deleteEdge(@PathVariable("id") Long id) {
        edgeService.deleteEdge(id);
    }
}
