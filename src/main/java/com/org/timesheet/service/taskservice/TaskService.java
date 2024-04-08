package com.org.timesheet.service.taskservice;

import com.org.timesheet.dto.timesheetdto.TaskDTO;
import com.org.timesheet.exception.CommonException;
import com.org.timesheet.model.timesheet.Project;
import com.org.timesheet.model.timesheet.Task;
import com.org.timesheet.model.userentity.User;
import com.org.timesheet.repository.timesheetRepository.ProjectRepository;
import com.org.timesheet.repository.timesheetRepository.TimeSheetRepository;
import com.org.timesheet.repository.userrepository.UserRepository;
import com.org.timesheet.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TimeSheetRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public TaskDTO saveTask(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId()).orElseThrow(()->new CommonException("User Not Found"));
        Project project = projectRepository.findById(taskDTO.getProjectId()).orElseThrow(()->new CommonException("Project Not Found"));
        Task task_Data = new Task(taskDTO,user,project);
        return convertToDTO(taskRepository.save(task_Data));
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(()->new CommonException("Task Not Found"));
    }

    public List<TaskDTO> getAllTask() {
        List<Task> taskData = taskRepository.findAll();
        return convertToDTO(taskData);
    }

    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        Task taskData = getTaskById(taskId);
        if (taskData != null) {
            // Retrieve Project and User entities
            Project project = projectRepository.findById(taskDTO.getProjectId())
                    .orElseThrow(() -> new CommonException("Project not found with ID: " + taskDTO.getProjectId()));
            User user = userRepository.findById(taskDTO.getUserId())
                    .orElseThrow(() -> new CommonException("User not found with ID: " + taskDTO.getUserId()));

            // Update task details
            taskData.setDescription(taskDTO.getDescription());
            taskData.setTags(taskDTO.getTags());
            taskData.setProject(project);
            taskData.setUser(user);

            return convertToDTO(taskRepository.save(taskData));
        }
        return null;
    }

    public Task endTask(Long taskId) {
        Task task = getTaskById(taskId);
        task.setEndTime(LocalDateTime.now());
        task.setDuration(calculateDuration(task.getStartTime(), task.getEndTime()));
        return taskRepository.save(task);

    }

    public static long calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        // Calculate the duration between start time and end time
        Duration duration = Duration.between(startTime, endTime);
        // Convert duration to milliseconds
        return duration.toMillis();
    }

    public Task startTask(Long taskId) {
        Task task = getTaskById(taskId);
        task.setStartTime(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public List<TaskDTO> getAllTaskByUserId(long userId) {
        List<Task> taskData =  taskRepository.findByUserId(userId);
        return convertToDTO(taskData);

    }

    public List<TaskDTO> convertToDTO(List<Task> tasks) {
        List<TaskDTO> taskDTOs = new ArrayList<>();
        for (Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setTaskId(task.getTask_id());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setDuration(task.getDuration());
            taskDTO.setTags(task.getTags());
            taskDTO.setStartTime(task.getStartTime());
            taskDTO.setEndTime(task.getEndTime());
            taskDTO.setUserId(task.getUser().getUser_id()); // Assuming getUserId returns the ID of the user
            taskDTO.setProjectId(task.getProject().getId()); // Assuming getId returns the ID of the project
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }

    public TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTask_id());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDuration(task.getDuration());
        taskDTO.setTags(task.getTags());
        taskDTO.setStartTime(task.getStartTime());
        taskDTO.setEndTime(task.getEndTime());
        taskDTO.setUserId(task.getUser().getUser_id()); // Assuming getUserId returns the ID of the user
        taskDTO.setProjectId(task.getProject().getId()); // Assuming getId returns the ID of the project
        return taskDTO;
    }
}
