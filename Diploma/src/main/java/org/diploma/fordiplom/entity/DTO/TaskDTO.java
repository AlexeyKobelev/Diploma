package org.diploma.fordiplom.entity.DTO;

public class TaskDTO {
    private Long id;
    private String title;
    private Long sprintId;

    private String taskType;

    private String taskKey;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public TaskDTO(Long id, String title, Long sprintId, String taskKey, String taskType) {
        this.id = id;
        this.title = title;
        this.sprintId = sprintId;
        this.taskKey = taskKey;
        this.taskType = taskType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }
}
