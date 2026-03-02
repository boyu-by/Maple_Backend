package com.example.maple.service;

import com.example.maple.model.dto.EdgeDTO;
import com.example.maple.model.entity.EdgeEntity;
import com.example.maple.model.entity.MindMapEntity;
import com.example.maple.model.entity.NodeEntity;
import com.example.maple.repository.EdgeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EdgeService {

    private final EdgeRepository edgeRepository;

    public EdgeService(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    public EdgeDTO createEdge(Long mindMapId, Long sourceNodeId, Long targetNodeId) {
        EdgeEntity entity = new EdgeEntity();
        MindMapEntity mindMap = new MindMapEntity();
        mindMap.setId(mindMapId);
        entity.setMindMap(mindMap);

        NodeEntity sourceNode = new NodeEntity();
        sourceNode.setId(sourceNodeId);
        entity.setSourceNode(sourceNode);

        NodeEntity targetNode = new NodeEntity();
        targetNode.setId(targetNodeId);
        entity.setTargetNode(targetNode);

        EdgeEntity saved = edgeRepository.save(entity);
        return toDto(saved);
    }

    public List<EdgeDTO> getEdgesByMindMapId(Long mindMapId) {
        return edgeRepository.findByMindMapId(mindMapId).stream()
                .map(this::toDto)
                .toList();
    }

    public void deleteEdge(Long edgeId) {
        edgeRepository.deleteById(edgeId);
    }

    private EdgeDTO toDto(EdgeEntity entity) {
        EdgeDTO dto = new EdgeDTO();
        dto.setId(entity.getId());
        dto.setMindMapId(entity.getMindMap() == null ? null : entity.getMindMap().getId());
        dto.setSourceNodeId(entity.getSourceNode() == null ? null : entity.getSourceNode().getId());
        dto.setTargetNodeId(entity.getTargetNode() == null ? null : entity.getTargetNode().getId());
        return dto;
    }
}
