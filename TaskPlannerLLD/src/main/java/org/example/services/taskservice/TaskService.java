package org.example.services.taskservice;

import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.models.TaskType;
import org.example.models.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface TaskService {
    public Task createTask(String title, long creator, LocalDateTime dueDate, TaskType type, Map<String, Object> metadata);
    public void changeTaskStatus(long id, TaskStatus status);
    public void changeTaskAsignee(long taskId, long asigneeId);
    public Optional<Task> getTaskById(long taskId);
}
