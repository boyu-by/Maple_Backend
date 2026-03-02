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

    public NodeDTO createNode(Long mindMapId, Long parentNodeId, String content, Double x, Double y) {
        NodeEntity entity = new NodeEntity();
        entity.setMindMapId(mindMapId);
        entity.setParentNodeId(parentNodeId);
        entity.setContent(content);
        entity.setX(x);
        entity.setY(y);
        entity.setCollapsed(false);
        NodeEntity saved = nodeRepository.save(entity);
        return toDto(saved);
    }

    public void updateNodeContent(Long nodeId, String content) {
        NodeEntity entity = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new EntityNotFoundException("Node not found: " + nodeId));
        entity.setContent(content);
    }

    public void moveNode(Long nodeId, Double x, Double y) {
        NodeEntity entity = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new EntityNotFoundException("Node not found: " + nodeId));
        entity.setX(x);
        entity.setY(y);
    }

    public void deleteNode(Long nodeId) {
        List<Long> idsToDelete = collectSubtreeIds(nodeId);
        for (Long id : idsToDelete) {
            edgeRepository.deleteBySourceNodeIdOrTargetNodeId(id, id);
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
        dto.setId(entity.getId());
        dto.setMindMapId(entity.getMindMapId());
        dto.setParentNodeId(entity.getParentNodeId());
        dto.setContent(entity.getContent());
        dto.setX(entity.getX());
        dto.setY(entity.getY());
        dto.setCollapsed(entity.getCollapsed());
        return dto;
    }
}
