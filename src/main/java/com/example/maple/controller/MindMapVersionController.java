package com.example.maple.controller;

import com.example.maple.model.dto.MindMapVersionDTO;
import com.example.maple.model.dto.MindMapDataDTO;
import com.example.maple.service.MindMapVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mind-maps/{mindMapId}/versions")
public class MindMapVersionController {

    @Autowired
    private MindMapVersionService versionService;

    /**
     * Create a new version of a mind map with the provided data.
     */
    @PostMapping
    public ResponseEntity<?> createVersion(@PathVariable Long mindMapId, @RequestBody MindMapDataDTO mapData) {
        try {
            MindMapVersionDTO version = versionService.createVersion(mindMapId, mapData);
            return ResponseEntity.ok(version);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create version: " + e.getMessage());
        }
    }

    /**
     * Get all versions of a mind map.
     */
    @GetMapping
    public ResponseEntity<?> getVersions(@PathVariable Long mindMapId) {
        try {
            List<MindMapVersionDTO> versions = versionService.getVersions(mindMapId);
            return ResponseEntity.ok(versions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get versions: " + e.getMessage());
        }
    }

    /**
     * Get a specific version of a mind map.
     */
    @GetMapping("/{version}")
    public ResponseEntity<?> getVersion(@PathVariable Long mindMapId, @PathVariable Integer version) {
        try {
            MindMapVersionDTO versionDTO = versionService.getVersion(mindMapId, version);
            return ResponseEntity.ok(versionDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Version not found: " + e.getMessage());
        }
    }

    /**
     * Restore a mind map to a specific version.
     */
    @PostMapping("/{version}/restore")
    public ResponseEntity<?> restoreVersion(@PathVariable Long mindMapId, @PathVariable Integer version) {
        try {
            MindMapVersionDTO restoredVersion = versionService.restoreVersion(mindMapId, version);
            return ResponseEntity.ok(restoredVersion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to restore version: " + e.getMessage());
        }
    }

    /**
     * Delete a specific version of a mind map.
     */
    @DeleteMapping("/{version}")
    public ResponseEntity<?> deleteVersion(@PathVariable Long mindMapId, @PathVariable Integer version) {
        try {
            versionService.deleteVersion(mindMapId, version);
            return ResponseEntity.ok("Version deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Version not found: " + e.getMessage());
        }
    }
}