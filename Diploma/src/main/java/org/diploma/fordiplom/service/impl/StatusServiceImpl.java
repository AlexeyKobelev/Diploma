package org.diploma.fordiplom.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.diploma.fordiplom.entity.DTO.request.StatusRequest;
import org.diploma.fordiplom.entity.ProjectEntity;
import org.diploma.fordiplom.entity.ProjectStatusEntity;
import org.diploma.fordiplom.entity.TaskStatusEntity;
import org.diploma.fordiplom.repository.ProjectRepository;
import org.diploma.fordiplom.repository.ProjectStatusRepository;
import org.diploma.fordiplom.repository.TaskStatusRepository;
import org.diploma.fordiplom.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    ProjectStatusRepository projectStatus;

    @Autowired
    TaskStatusRepository taskStatusRepository;

    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public ProjectStatusEntity setDefaultStatus(ProjectEntity project) {
        // 1. Проверяем и сохраняем проект, если он новый


        // 2. Создаем или получаем стандартные статусы
        List<TaskStatusEntity> defaultStatuses = new ArrayList<>();

        // Добавляем статус "К выполнению" (исправлена опечатка)
        TaskStatusEntity todoStatus = taskStatusRepository.findByName("К выполнению").orElse(null);
        defaultStatuses.add(todoStatus);

        // Добавляем статус "В работе"
        TaskStatusEntity inProgressStatus = taskStatusRepository.findByName("В работе").orElse(null);
        defaultStatuses.add(inProgressStatus);

        // Добавляем статус "Готово"
        TaskStatusEntity doneStatus = taskStatusRepository.findByName("Готово").orElse(null);
        defaultStatuses.add(doneStatus);

        // 3. Создаем и сохраняем связи проекта со статусами
        int position = 0;
        ProjectStatusEntity firstStatus = null;

        for (TaskStatusEntity status : defaultStatuses) {
            ProjectStatusEntity projectStatus = new ProjectStatusEntity();
            projectStatus.setProject(project);
            projectStatus.setStatus(status); // Гарантированно не null
            projectStatus.setPosition(position++);

            ProjectStatusEntity savedStatus = projectStatusRepository.save(projectStatus);
            if (firstStatus == null) {
                firstStatus = savedStatus; // Запоминаем первый статус
            }
        }

        if (firstStatus == null) {
            throw new IllegalStateException("Не удалось создать ни одного статуса для проекта");
        }

        return firstStatus;
    }

    @Override
    public List<ProjectStatusEntity> getStatusByProject(Long id) {
        return projectStatus.findByProject_Id(id);
    }

    @Override
    public TaskStatusEntity getStatusById(Integer id) {
        return taskStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TaskStatus not found with id: " + id));
    }

    @Override
    public ProjectStatusEntity createCustomStatus(StatusRequest request) {
        taskStatusRepository.findByName(request.getName())
                .ifPresent(existingStatus -> {
                    throw new EntityExistsException("Status with name '" + request.getName() + "' already exists");
                });
        TaskStatusEntity taskStatus = new TaskStatusEntity();
        taskStatus.setName(request.getName().trim());
        TaskStatusEntity savedStatus = taskStatusRepository.save(taskStatus);
        ProjectStatusEntity projectStatus = new ProjectStatusEntity();
        if (request.getProjectId() != null) {
            ProjectEntity project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + request.getProjectId()));

            //ProjectStatusEntity projectStatus = new ProjectStatusEntity();
            projectStatus.setProject(project);
            projectStatus.setStatus(savedStatus);
            projectStatus.setPosition(getNextPositionForProject(project.getId()));

            //return projectStatusRepository.save(projectStatus);
        }

        // Если projectId не указан, возвращаем просто созданный статус
        // (хотя это может потребовать изменения возвращаемого типа метода)
        return projectStatusRepository.save(projectStatus); // или можно изменить сигнатуру метода
    }

    private Integer getNextPositionForProject(Long projectId) {
        return projectStatusRepository.findFirstByProject_IdOrderByPositionDesc(projectId)
                .map(ProjectStatusEntity::getPosition)
                .map(position -> position + 1)
                .orElse(0);
    }
}


