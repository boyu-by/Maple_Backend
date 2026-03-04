package com.example.maple.repository;

import com.example.maple.model.entity.MindMapVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MindMapVersionRepository extends JpaRepository<MindMapVersionEntity, Long> {

    /**
     * Find all versions for a specific mind map, ordered by version number descending.
     */
    List<MindMapVersionEntity> findByMindMapIdOrderByVersionDesc(Long mindMapId);

    /**
     * Find the latest version for a specific mind map.
     */
    MindMapVersionEntity findFirstByMindMapIdOrderByVersionDesc(Long mindMapId);

    /**
     * Find a specific version of a mind map.
     */
    MindMapVersionEntity findByMindMapIdAndVersion(Long mindMapId, Integer version);

    /**
     * Delete all versions for a specific mind map.
     */
    void deleteByMindMapId(Long mindMapId);
}