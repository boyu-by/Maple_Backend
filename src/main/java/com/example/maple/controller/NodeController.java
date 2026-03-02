package com.example.maple.controller;

import com.example.maple.model.dto.NodeDTO;
import com.example.maple.service.NodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nodes")
@Tag(name = "Node")
public class NodeController {

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping
    @Operation(summary = "Create node", description = "Create a node in a mind map.")
    public NodeDTO createNode(@RequestBody NodeDTO request) {
        return nodeService.createNode(
                request.getMindMapId(),
                request.getParentNodeId(),
                request.getContent(),
                request.getX(),
                request.getY()
        );
    }

    @PutMapping("/{id}/content")
    @Operation(summary = "Update node content", description = "Update the content of a node.")
    public void updateNodeContent(@PathVariable("id") Long id, @RequestBody NodeDTO request) {
        nodeService.updateNodeContent(id, request.getContent());
    }

    @PutMapping("/{id}/position")
    @Operation(summary = "Move node", description = "Update the position of a node.")
    public void moveNode(@PathVariable("id") Long id, @RequestBody NodeDTO request) {
        nodeService.moveNode(id, request.getX(), request.getY());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete node", description = "Delete a node and its descendants.")
    public void deleteNode(@PathVariable("id") Long id) {
        nodeService.deleteNode(id);
    }

    @GetMapping
    @Operation(summary = "List nodes", description = "List nodes by mind map id.")
    public List<NodeDTO> listNodes(@RequestParam("mindMapId") Long mindMapId) {
        return nodeService.getNodesByMindMapId(mindMapId);
    }
}