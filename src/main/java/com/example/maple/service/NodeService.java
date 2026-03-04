package com.example.maple.service;

import com.example.maple.model.dto.NodeDTO;
import com.example.maple.model.entity.NodeEntity;
import com.example.maple.repository.EdgeRepository;
import com.example.maple.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NodeService {

    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;

    public NodeService(NodeRepository nodeRepository, EdgeRepository edgeRepository) {
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
    }

    public NodeDTO createNode(String text, Double x, Double y, String parentId) {
        NodeEntity entity = new NodeEntity();
        // TODO: Set mindMapId from context or request
        entity.setParentNodeId(parentId != null ? Long.parseLong(parentId) : null);
        entity.setContent(text);
        entity.setX(x);
        entity.setY(y);
        entity.setCollapsed(false);
        NodeEntity saved = nodeRepository.save(entity);
        return toDto(saved);
    }

    public List<NodeDTO> getNodesByMindMapId(Long mindMapId) {
        return nodeRepository.findByMindMapId(mindMapId).stream()
                .map(this::toDto)
                .toList();
    }

    public void updateNodeContent(String id, String text) {
        NodeEntity entity = nodeRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new EntityNotFoundException("Node not found: " + id));
        entity.setContent(text);
    }

    public void moveNode(String id, Double x, Double y) {
        NodeEntity entity = nodeRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new EntityNotFoundException("Node not found: " + id));
        entity.setX(x);
        entity.setY(y);
    }

    public void deleteNode(String id) {
        List<Long> idsToDelete = collectSubtreeIds(Long.parseLong(id));
        for (Long nodeId : idsToDelete) {
            edgeRepository.deleteBySourceNodeIdOrTargetNodeId(nodeId, nodeId);
        }
        nodeRepository.deleteAllById(idsToDelete);
    }

    private List<Long> collectSubtreeIds(Long rootId) {
        Set<Long> visited = new LinkedHashSet<>();
        Deque<Long> stack = new ArrayDeque<>();
        stack.push(rootId);
        while (!stack.isEmpty()) {
            Long currentId = stack.pop();
            if (!visited.add(currentId)) {
                continue;
            }
            List<NodeEntity> children = nodeRepository.findByParentNodeId(currentId);
            for (NodeEntity child : children) {
                if (child.getId() != null) {
                    stack.push(child.getId());
                }
            }
        }
        return new ArrayList<>(visited);
    }

    private NodeDTO toDto(NodeEntity entity) {
        NodeDTO dto = new NodeDTO();
        dto.setId(entity.getId().toString());
        dto.setText(entity.getContent());
        dto.setX(entity.getX());
        dto.setY(entity.getY());
        dto.setParentId(entity.getParentNodeId() != null ? entity.getParentNodeId().toString() : null);
        // TODO: Set children list
        dto.setCollapsed(entity.getCollapsed());
        return dto;
    }
}
