package org.diploma.fordiplom.repository;

import org.diploma.fordiplom.entity.TaskEntity;
import org.diploma.fordiplom.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
   Optional<UserEntity> findByEmail(String email);
   List<UserEntity> findAllByEmailIn(List<String> emails);
   Optional<UserEntity> findByConfirmationToken(String token);
   @Query("""
        SELECT DISTINCT t.executor 
        FROM TaskEntity t 
        WHERE t.executor IS NOT NULL AND t.sprint.project.id = :projectId
    """)
   List<UserEntity> findDistinctByTasksSprintProjectId(@Param("projectId") Long projectId);
}
