package org.example.services;

import org.example.exceptions.TaskPlannerException;
import org.example.models.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskPlannerService {

    public Sprint createSprint();
    public Task createTask(String title, long creator, LocalDateTime dueDate, TaskType type, Map<String, Object> metadata);
    public void addSprintTask(long sprintId, long taskId);
    public void removeSprintTask(long sprintId, long taskId);
    public void changeTaskStatus(long taskId, TaskStatus taskStatus);
    public void changeTaskAsignee(long taskId, long userId);
    public User createUser();
    public Map<TaskType, List<Task>> getUserTasksByType(long userId);
    public Map<Task, TaskTimelineStatus> getSprintSnapshot(long sprintId);
}
