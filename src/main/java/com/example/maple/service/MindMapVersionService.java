package com.example.maple.service;

import com.example.maple.model.entity.MindMapEntity;
import com.example.maple.model.entity.MindMapVersionEntity;
import com.example.maple.model.dto.MindMapVersionDTO;
import com.example.maple.model.dto.MindMapDataDTO;
import com.example.maple.model.dto.NodeDTO;
import com.example.maple.repository.MindMapRepository;
import com.example.maple.repository.MindMapVersionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MindMapVersionService {

    @Autowired
    private MindMapRepository mindMapRepository;

    @Autowired
    private MindMapVersionRepository versionRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Create a new version of a mind map with the provided data.
     */
    @Transactional
    public MindMapVersionDTO createVersion(Long mindMapId, MindMapDataDTO mapData) throws Exception {
        return createVersion(mindMapId, "anonymous", mapData);
    }

    /**
     * Create a new version of a mind map with the provided data and creator.
     */
    @Transactional
    public MindMapVersionDTO createVersion(Long mindMapId, String createdBy, MindMapDataDTO mapData) throws Exception {
        MindMapEntity mindMap = mindMapRepository.findById(mindMapId)
                .orElseGet(() -> {
                    // 如果没有找到思维导图，自动创建一个
                    MindMapEntity newMindMap = new MindMapEntity();
                    newMindMap.setTitle("默认思维导图");
                    return mindMapRepository.save(newMindMap);
                });

        // Get the latest version number
        MindMapVersionEntity latestVersion = versionRepository.findFirstByMindMapIdOrderByVersionDesc(mindMapId);
        Integer newVersionNumber = latestVersion != null ? latestVersion.getVersion() + 1 : 1;

        // Create new version entity
        MindMapVersionEntity version = new MindMapVersionEntity();
        version.setMindMap(mindMap);
        version.setVersion(newVersionNumber);
        version.setTitle(mindMap.getTitle());
        version.setDescription(mindMap.getDescription());
        version.setLayoutDirection(mindMap.getLayoutDirection() != null ? mindMap.getLayoutDirection().name() : "left-right");
        version.setMapData(objectMapper.writeValueAsString(mapData));
        version.setCreatedBy(createdBy);

        MindMapVersionEntity savedVersion = versionRepository.save(version);
        return convertToDTO(savedVersion);
    }

    /**
     * Create a new version of a mind map.
     */
    @Transactional
    public MindMapVersionDTO createVersion(Long mindMapId, String createdBy) throws Exception {
        MindMapEntity mindMap = mindMapRepository.findById(mindMapId)
                .orElseThrow(() -> new Exception("Mind map not found"));

        // Get the latest version number
        MindMapVersionEntity latestVersion = versionRepository.findFirstByMindMapIdOrderByVersionDesc(mindMapId);
        Integer newVersionNumber = latestVersion != null ? latestVersion.getVersion() + 1 : 1;

        // Create a snapshot of the mind map data
        MindMapDataDTO mapData = new MindMapDataDTO();
        // TODO: Populate mapData with actual nodes and rootIds

        String mapDataJson = objectMapper.writeValueAsString(mapData);

        // Create new version entity
        MindMapVersionEntity version = new MindMapVersionEntity();
        version.setMindMap(mindMap);
        version.setVersion(newVersionNumber);
        version.setTitle(mindMap.getTitle());
        version.setDescription(mindMap.getDescription());
        version.setLayoutDirection(mindMap.getLayoutDirection().name());
        version.setMapData(mapDataJson);
        version.setCreatedBy(createdBy);

        MindMapVersionEntity savedVersion = versionRepository.save(version);
        return convertToDTO(savedVersion);
    }

    /**
     * Get all versions of a mind map.
     */
    public List<MindMapVersionDTO> getVersions(Long mindMapId) {
        List<MindMapVersionEntity> versions = versionRepository.findByMindMapIdOrderByVersionDesc(mindMapId);
        return versions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific version of a mind map.
     */
    public MindMapVersionDTO getVersion(Long mindMapId, Integer version) throws Exception {
        MindMapVersionEntity versionEntity = versionRepository.findByMindMapIdAndVersion(mindMapId, version);
        if (versionEntity == null) {
            throw new Exception("Version not found");
        }
        return convertToDTO(versionEntity);
    }

    /**
     * Restore a mind map to a specific version.
     */
    @Transactional
    public MindMapVersionDTO restoreVersion(Long mindMapId, Integer version) throws Exception {
        MindMapVersionEntity versionEntity = versionRepository.findByMindMapIdAndVersion(mindMapId, version);
        if (versionEntity == null) {
            throw new Exception("Version not found");
        }

        // Get the mind map
        MindMapEntity mindMap = mindMapRepository.findById(mindMapId)
                .orElseThrow(() -> new Exception("Mind map not found"));

        // Update the mind map with the version data
        mindMap.setTitle(versionEntity.getTitle());
        mindMap.setDescription(versionEntity.getDescription());
        mindMap.setLayoutDirection(com.example.maple.model.entity.LayoutDirection.valueOf(versionEntity.getLayoutDirection()));

        mindMapRepository.save(mindMap);

        // Return the version entity with mapData
        return convertToDTO(versionEntity);
    }

    /**
     * Delete a specific version of a mind map.
     */
    @Transactional
    public void deleteVersion(Long mindMapId, Integer version) throws Exception {
        MindMapVersionEntity versionEntity = versionRepository.findByMindMapIdAndVersion(mindMapId, version);
        if (versionEntity == null) {
            throw new Exception("Version not found");
        }
        versionRepository.delete(versionEntity);
    }

    /**
     * Convert MindMapVersionEntity to MindMapVersionDTO.
     */
    private MindMapVersionDTO convertToDTO(MindMapVersionEntity entity) {
        MindMapVersionDTO dto = new MindMapVersionDTO();
        dto.setId(entity.getId());
        dto.setMindMapId(entity.getMindMap().getId());
        dto.setVersion(entity.getVersion());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setLayoutDirection(entity.getLayoutDirection());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        try {
            System.out.println("MapData JSON: " + entity.getMapData());
            MindMapDataDTO mapData = objectMapper.readValue(entity.getMapData(), MindMapDataDTO.class);
            System.out.println("MapData DTO: " + mapData);
            dto.setMapData(mapData);
        } catch (Exception e) {
            System.out.println("Error parsing mapData: " + e.getMessage());
            // Ignore parsing error for now
        }
        return dto;
    }
}