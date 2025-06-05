package org.diploma.fordiplom.entity.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskLocationUpdateRequest {
    private Long taskId;
    private Long sprintId;
    private Integer position;

}
