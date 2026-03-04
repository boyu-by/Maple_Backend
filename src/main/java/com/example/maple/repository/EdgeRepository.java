package com.example.maple.repository;

import com.example.maple.model.entity.EdgeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdgeRepository extends JpaRepository<EdgeEntity, Long> {

    List<EdgeEntity> findByMindMapId(Long mindMapId);

    List<EdgeEntity> findBySourceNodeIdOrTargetNodeId(Long sourceNodeId, Long targetNodeId);

    void deleteBySourceNodeIdOrTargetNodeId(Long sourceNodeId, Long targetNodeId);

    void deleteByMindMapId(Long mindMapId);
}