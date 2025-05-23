package org.diploma.fordiplom.controller;


import org.diploma.fordiplom.entity.DTO.request.SprintRequest;
import org.diploma.fordiplom.entity.DTO.response.SprintResponse;
import org.diploma.fordiplom.entity.SprintEntity;
import org.diploma.fordiplom.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SprintController {
    @Autowired SprintService sprintService;

    @PostMapping(path = "/create_sprint", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SprintEntity createNewSprint(@RequestBody SprintRequest sprint) {
        return sprintService.createSprint(sprint);
    }
    @GetMapping("/api/project/{projectId}/sprints")
    public List<SprintResponse> getSprintsByProjectId(@PathVariable Long projectId) {
        return sprintService.getSprintsByProjectId(projectId);
    }

}
