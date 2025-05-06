package org.diploma.fordiplom.repository;

import org.diploma.fordiplom.entity.TaskStatusEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository <TaskStatusEntity, Integer> {
    @NotNull Optional<TaskStatusEntity> findById(@NotNull Integer id);

    Optional<TaskStatusEntity> findByName(String name);
}
