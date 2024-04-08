package com.org.timesheet.service.taskservice;

import com.org.timesheet.dto.timesheetdto.ProjectDTO;
import com.org.timesheet.dto.timesheetdto.TaskDTO;
import com.org.timesheet.exception.CommonException;
import com.org.timesheet.model.timesheet.Project;
import com.org.timesheet.model.timesheet.Task;
import com.org.timesheet.model.userentity.User;
import com.org.timesheet.repository.timesheetRepository.ProjectRepository;
import com.org.timesheet.repository.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectDTO addProjectForUser(Long userId, ProjectDTO projectDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CommonException("User not found with id: " + userId);
        }

        // Convert DTO to entity
        Project project = new Project();
        project.setName(projectDTO.getName());

        // Set the user for the project
        User user = userOptional.get();
        project.setTasks(new ArrayList<>()); // Initialize tasks list
        project.getTasks().forEach(task -> task.setProject(project)); // Set project for each task
        projectRepository.save(project);

        // Convert the saved project entity back to DTO
        ProjectDTO savedProjectDTO = new ProjectDTO();
        savedProjectDTO.setId(project.getId());
        savedProjectDTO.setName(project.getName());
        savedProjectDTO.setTasks(project.getTasks().stream()
                .map(this::convertTaskToDTO)
                .collect(Collectors.toList()));

        return savedProjectDTO;
    }

    // Helper method to convert Task entity to DTO
    private TaskDTO convertTaskToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTask_id());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setTags(task.getTags());
        taskDTO.setStartTime(task.getStartTime());
        taskDTO.setEndTime(task.getEndTime());
        taskDTO.setDuration(task.getDuration());
        // Omitting user and project details for brevity
        return taskDTO;
    }

}
