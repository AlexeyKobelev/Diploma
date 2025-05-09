package org.diploma.fordiplom.repository;

import org.diploma.fordiplom.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {    @Query("SELECT t.taskKey FROM TaskEntity t WHERE t.taskKey LIKE CONCAT(:projectKey, '-%') ORDER BY LENGTH(t.taskKey) DESC, t.taskKey DESC")
    List<String> findTaskKeysByProjectKey(@Param("projectKey") String projectKey);
    List<TaskEntity> findBySprintId(Long sprintId);
    List<TaskEntity> findByProject_IdAndSprintIsNull(Long projectId);
}
