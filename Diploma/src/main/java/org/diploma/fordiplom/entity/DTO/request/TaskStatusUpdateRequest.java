package org.diploma.fordiplom.entity.DTO.request;

import org.diploma.fordiplom.entity.ProjectStatusEntity;

public class TaskStatusUpdateRequest {
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
