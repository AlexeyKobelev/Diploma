package org.diploma.fordiplom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_status", schema = "diploma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name; // Например: "В работе", "Готово", "К проверке"
}

