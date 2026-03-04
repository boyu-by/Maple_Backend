package com.example.maple.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mind_map_version")
@Getter
@Setter
@NoArgsConstructor
public class MindMapVersionEntity {

    /** Primary key for the version. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The mind map this version belongs to. */
    @ManyToOne
    @JoinColumn(name = "mind_map_id", nullable = false)
    private MindMapEntity mindMap;

    /** Version number. */
    @Column(nullable = false)
    private Integer version;

    /** Title of the mind map at this version. */
    @Column(nullable = false)
    private String title;

    /** Description of the mind map at this version. */
    @Column
    private String description;

    /** Layout direction at this version. */
    @Column(name = "layout_direction", nullable = false)
    private String layoutDirection;

    /** Snapshot of the mind map data as JSON. */
    @Column(name = "map_data", columnDefinition = "TEXT", nullable = false)
    private String mapData;

    /** Creation timestamp of this version. */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** User who created this version. */
    @Column(name = "created_by")
    private String createdBy;

    @PrePersist
    void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}