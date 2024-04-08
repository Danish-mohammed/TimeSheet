package com.org.timesheet.controller.timesheetcontroller;

import com.org.timesheet.dto.timesheetdto.TaskDTO;
import com.org.timesheet.model.timesheet.Task;
import com.org.timesheet.service.taskservice.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TimeSheetController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.saveTask(taskDTO);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<TaskDTO> getAllTask() {
        return taskService.getAllTask();
    }

    @GetMapping("/getbyuser/{userId}")
    public List<TaskDTO> getAllTaskByUser(@PathVariable long userId) {
        return taskService.getAllTaskByUserId(userId);
    }

    @PutMapping("/edit/{taskId}")
    public TaskDTO editTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDetails) {
        return taskService.updateTask(taskId,taskDetails);
    }

    @PutMapping("/start/{taskId}")
    public Task startTask(@PathVariable Long taskId) {
        return taskService.startTask(taskId);
    }

    @PutMapping("/stop/{taskId}")
    public Task stopTask(@PathVariable Long taskId) {
        return taskService.endTask(taskId);
    }
}
