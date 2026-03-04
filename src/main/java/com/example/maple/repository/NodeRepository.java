package com.example.maple.repository;

import com.example.maple.model.entity.NodeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

    List<NodeEntity> findByMindMapId(Long mindMapId);

    List<NodeEntity> findByParentNodeId(Long parentNodeId);

    List<NodeEntity> findByMindMapIdAndParentNodeIdIsNull(Long mindMapId);

    List<NodeEntity> findByIdIn(List<Long> ids);

    void deleteByMindMapId(Long mindMapId);
}