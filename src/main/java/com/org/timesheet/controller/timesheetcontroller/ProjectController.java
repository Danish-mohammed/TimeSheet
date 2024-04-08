package com.org.timesheet.controller.timesheetcontroller;

import com.org.timesheet.dto.timesheetdto.ProjectDTO;
import com.org.timesheet.model.timesheet.Project;
import com.org.timesheet.model.timesheet.Task;
import com.org.timesheet.service.taskservice.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/{userId}")
    public ResponseEntity<ProjectDTO> addProjectForUser(@PathVariable Long userId, @RequestBody ProjectDTO projectDTO) {
        ProjectDTO savedProject = projectService.addProjectForUser(userId, projectDTO);
        if (savedProject != null) {
            return ResponseEntity.ok(savedProject);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
