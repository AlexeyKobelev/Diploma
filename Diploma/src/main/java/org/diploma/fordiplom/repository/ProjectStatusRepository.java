package org.diploma.fordiplom.repository;

import org.diploma.fordiplom.entity.ProjectEntity;
import org.diploma.fordiplom.entity.ProjectStatusEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatusEntity, Integer> {
    List<ProjectStatusEntity> findByProject_Id(Long projectId);

    Optional<ProjectStatusEntity> findFirstByProject_IdOrderByPositionDesc(Long projectId);

    Optional<ProjectStatusEntity> findByProject_IdAndStatusId(Long projectId, Long statusId);
}
