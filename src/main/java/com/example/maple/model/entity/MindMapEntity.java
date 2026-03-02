package com.example.maple.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mind_map")
@Getter
@Setter
@NoArgsConstructor
public class MindMapEntity {

    /** Primary key for the mind map. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Title shown to users; required. */
    @Column(nullable = false)
    private String title;

    /** Optional description to summarize the mind map. */
    @Column
    private String description;

    /** Layout direction for rendering; required. */
    @Enumerated(EnumType.STRING)
    @Column(name = "layout_direction", nullable = false)
    private LayoutDirection layoutDirection;

    /** Creation timestamp managed by JPA callbacks. */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /** Update timestamp managed by JPA callbacks. */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** Nodes that belong to this mind map. */
    @OneToMany(
            mappedBy = "mindMap",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    private Set<NodeEntity> nodes = new HashSet<>();

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.layoutDirection == null) {
            this.layoutDirection = LayoutDirection.LEFT_RIGHT;
        }
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
