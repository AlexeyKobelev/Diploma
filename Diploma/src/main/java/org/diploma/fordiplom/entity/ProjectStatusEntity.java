package org.diploma.fordiplom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_status", schema = "diploma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id")
    private TaskStatusEntity status;

    @Column(nullable = false)
    private Integer position; // позиция в рамках проекта
}

