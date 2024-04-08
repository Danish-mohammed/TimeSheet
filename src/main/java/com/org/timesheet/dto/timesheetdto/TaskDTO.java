package com.org.timesheet.dto.timesheetdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long taskId;
    private String description;
    private String tags;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private Long userId; // Foreign key instead of full User object
    private Long projectId; // Foreign key instead of full Project object
}
