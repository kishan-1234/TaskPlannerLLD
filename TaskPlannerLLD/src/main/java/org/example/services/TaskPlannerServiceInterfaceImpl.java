package org.example.services;

import org.example.exceptions.TaskPlannerException;
import org.example.models.*;
import org.example.services.sprintservice.SprintService;
import org.example.services.sprintservice.SprintServiceImpl;
import org.example.services.taskservice.TaskService;
import org.example.services.taskservice.TaskServiceImpl;
import org.example.services.userservice.UserService;
import org.example.services.userservice.UserServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskPlannerServiceInterfaceImpl implements TaskPlannerService {

    private SprintService sprintService = SprintServiceImpl.getInstance();
    private TaskService taskService = TaskServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Sprint createSprint() {
        return sprintService.createSprint();
    }

    @Override
    public Task createTask(String title, long creator, LocalDateTime dueDate, TaskType type, Map<String, Object> metadata) {
        return taskService.createTask(title, creator, dueDate, type, metadata);
    }

    @Override
    public void addSprintTask(long sprintId, long taskId) {

        Sprint sprint = sprintService.getSprintById(sprintId)
                .orElseThrow(() -> new TaskPlannerException("Sprint not found with ID: " + sprintId));
        try {
            sprint.getTaskById(taskId);
        } catch(TaskPlannerException e) {
            sprint.addTask(taskId);
            Task task = taskService.getTaskById(taskId).orElseThrow(() -> new TaskPlannerException("Task not found with taskId: "+ taskId));
            if(task.getSprintId()!=0)
                throw new TaskPlannerException("Task is already added to a sprint, taskId: "+task.getId());
            task.setSprintId(sprintId);
            return;
        }
        throw new TaskPlannerException("Sprint already contains taskId: "+taskId);
    }

    @Override
    public void removeSprintTask(long sprintId, long taskId) {

        Sprint sprint = sprintService.getSprintById(sprintId)
                .orElseThrow(() -> new TaskPlannerException("Sprint not found with ID: " + sprintId));
        sprint.removeTask(taskId);
    }
    @Override
    public void changeTaskStatus(long taskId, TaskStatus taskStatus) {

        taskService.changeTaskStatus(taskId, taskStatus);
    }

    @Override
    public void changeTaskAsignee(long taskId, long userId) {

        Task task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new TaskPlannerException("Task not found with taskId: "+taskId));
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new TaskPlannerException("User not found with userId: "+userId));

        taskService.changeTaskAsignee(taskId, userId);
        userService.addUserTask(userId, taskId);
    }

    @Override
    public User createUser() {
        return userService.createUser();
    }

    @Override
    public Map<TaskType, List<Task>> getUserTasksByType(long userId) {

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new TaskPlannerException("User not found with userId: "+userId));
        List<Task> tasks = user.getTasks().stream().map(taskId ->
                taskService.getTaskById(taskId).orElseThrow(() -> new TaskPlannerException("Task not found with taskId: "+taskId))
        ).collect(Collectors.toList());
        return tasks.stream().collect(Collectors.groupingBy(Task::getTasktype));
    }

    @Override
    public Map<Task, TaskTimelineStatus> getSprintSnapshot(long sprintId) {

        Sprint sprint = sprintService.getSprintById(sprintId)
                .orElseThrow(() -> new TaskPlannerException("Sprint not found with sprintId: "+sprintId));
        List<Task> tasks =  sprint.getTasks().stream().map(taskId -> taskService.getTaskById(taskId)
                .orElseThrow(() -> new TaskPlannerException(""))
        ).collect(Collectors.toList());
        return tasks.stream()
                .collect(Collectors.toMap(
                        task -> task,
                        task -> task.getDueDate().isAfter(LocalDateTime.now())
                                ? TaskTimelineStatus.ON_TRACK
                                : TaskTimelineStatus.DELAYED
                ));
    }
}
