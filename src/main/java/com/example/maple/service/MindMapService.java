package com.example.maple.service;

import com.example.maple.model.dto.MindMapDTO;
import com.example.maple.model.entity.LayoutDirection;
import com.example.maple.model.entity.MindMapEntity;
import com.example.maple.repository.EdgeRepository;
import com.example.maple.repository.MindMapRepository;
import com.example.maple.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MindMapService {

    private final MindMapRepository mindMapRepository;
    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;

    public MindMapService(
            MindMapRepository mindMapRepository,
            NodeRepository nodeRepository,
            EdgeRepository edgeRepository
    ) {
        this.mindMapRepository = mindMapRepository;
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
    }

    public MindMapDTO createMindMap(String title) {
        MindMapEntity entity = new MindMapEntity();
        entity.setTitle(title);
        MindMapEntity saved = mindMapRepository.save(entity);
        return toDto(saved);
    }

    public void updateLayoutDirection(Long mindMapId, String layoutDirection) {
        MindMapEntity entity = mindMapRepository.findById(mindMapId)
                .orElseThrow(() -> new EntityNotFoundException("MindMap not found: " + mindMapId));
        entity.setLayoutDirection(LayoutDirection.valueOf(layoutDirection));
    }

    public void clearMindMap(Long mindMapId) {
        edgeRepository.deleteByMindMapId(mindMapId);
        nodeRepository.deleteByMindMapId(mindMapId);
    }

    private MindMapDTO toDto(MindMapEntity entity) {
        MindMapDTO dto = new MindMapDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setLayoutDirection(entity.getLayoutDirection() == null ? null : entity.getLayoutDirection().name());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}