package model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "edge")
@Getter
@Setter
@NoArgsConstructor
public class EdgeEntity {

    /** Primary key for the edge. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Owning mind map of this edge. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mind_map_id", nullable = false)
    private MindMapEntity mindMap;

    /** Source node of the edge. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_node_id", nullable = false)
    private NodeEntity sourceNode;

    /** Target node of the edge. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_node_id", nullable = false)
    private NodeEntity targetNode;

    /** Optional label describing the edge. */
    @Column
    private String label;

    /** Creation timestamp managed by JPA callback. */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
