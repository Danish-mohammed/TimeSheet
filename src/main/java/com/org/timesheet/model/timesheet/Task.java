package com.org.timesheet.model.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.timesheet.dto.timesheetdto.TaskDTO;
import com.org.timesheet.model.userentity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;
    private String description;
    private String tags;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration; // Duration in milliseconds

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties
    private Project project;

    public Task(TaskDTO task,User user,Project project) {
        this.description = task.getDescription();
        this.tags = task.getTags();
        this.user = user;
        this.project = project;
    }
}
