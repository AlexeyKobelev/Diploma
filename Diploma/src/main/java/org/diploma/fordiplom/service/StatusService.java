package org.diploma.fordiplom.service;

import org.diploma.fordiplom.entity.DTO.request.StatusRequest;
import org.diploma.fordiplom.entity.ProjectEntity;
import org.diploma.fordiplom.entity.ProjectStatusEntity;
import org.diploma.fordiplom.entity.TaskStatusEntity;

import java.util.List;

public interface StatusService {
    List<ProjectStatusEntity> getStatusByProject(Long id);

    TaskStatusEntity getStatusById(Integer id);

    ProjectStatusEntity createCustomStatus(StatusRequest name);

    ProjectStatusEntity setDefaultStatus(ProjectEntity project);

}
