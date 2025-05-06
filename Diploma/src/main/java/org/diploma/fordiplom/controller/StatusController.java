package org.diploma.fordiplom.controller;

import org.diploma.fordiplom.entity.DTO.request.SprintRequest;
import org.diploma.fordiplom.entity.DTO.request.StatusRequest;
import org.diploma.fordiplom.entity.ProjectEntity;
import org.diploma.fordiplom.entity.ProjectStatusEntity;
import org.diploma.fordiplom.entity.SprintEntity;
import org.diploma.fordiplom.entity.TaskStatusEntity;
import org.diploma.fordiplom.service.ProjectService;
import org.diploma.fordiplom.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StatusController {
    @Autowired
    StatusService statusService;

    @Autowired
    ProjectService projectService;

    @GetMapping("/api/status/{id}")
    public TaskStatusEntity getStatus(@PathVariable Integer id){
        return statusService.getStatusById(id);
    }

    @GetMapping("/api/project/status/{projectId}")
    public List<ProjectStatusEntity> getStatusByProject(@PathVariable Long projectId){
        return statusService.getStatusByProject(projectId);
    }

    @PostMapping(path = "/createCustomStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProjectStatusEntity createNewStatus(@RequestBody StatusRequest status) {
        return statusService.createCustomStatus(status);
    }

    @PostMapping(path = "/setDefaultStatus")
    public ProjectStatusEntity setDefaultStatus(@RequestBody ProjectEntity project){
        return statusService.setDefaultStatus(project);
    }
}
