package org.diploma.fordiplom.service;


import org.diploma.fordiplom.entity.DTO.TagDTO;
import org.diploma.fordiplom.entity.DTO.TaskDTO;
import org.diploma.fordiplom.entity.DTO.request.TaskPositionUpdateRequest;
import org.diploma.fordiplom.entity.DTO.request.TaskRequest;
import org.diploma.fordiplom.entity.TaskEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskEntity createTask(TaskRequest request);
    String keyGenerator(TaskRequest request);
    List<TaskEntity> getTasksBySprintId(Long sprintId);
    List<TaskEntity> getBackLogTasksByProjectId(Long projectId);
    void updateTaskLocation(Long taskId, Long sprintId, Integer position);
    void updateStatus(Long taskId, String status);
    List<TaskDTO> searchTasksInActiveSprints(String query, Long projectId);
    TaskEntity getTaskById(Long taskId);
    TaskEntity updateTaskTitle(Long taskId, String taskTitle);
    TaskEntity updateTaskDescription(Long taskId, String taskDescription);
    TaskEntity updateTaskPriority(Long taskId, String newPriority, String email);
    List<TagDTO> getTagsForTask(Long taskId);
    TagDTO addTagToTask(Long taskId, TagDTO tagDTO);
    void removeTagFromTask(Long taskId, Long tagId);
    List<TagDTO> searchTags(String query);
    void assignTeam(Long taskId, Long teamId);
    void assignExecutor(Long taskId, Long userId);
    void updateTaskPositions(List<TaskPositionUpdateRequest> updates);
    List<TaskDTO> getTaskDTOsBySprintId(Long sprintId);
    void assignSprint(Long taskId, Long sprintId);
    TaskDTO getTaskByKey(String key);

}
