package org.example.services.taskservice;

import org.example.exceptions.TaskPlannerException;
import org.example.models.*;
import org.example.services.userservice.UserServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TaskServiceImpl implements TaskService{

    private static TaskServiceImpl taskService = null;
    private static long taskIdCounter = 1;
    private TaskServiceImpl() {}
    private List<Task> tasks = new ArrayList<>();
    private static UserServiceImpl userService = UserServiceImpl.getInstance();

    public static TaskServiceImpl getInstance() {
        if(taskService==null) {
            synchronized (TaskServiceImpl.class) {
                if(taskService==null) {
                    taskService =  new TaskServiceImpl();
                }
            }
        }
        return taskService;
    }

    @Override
    public Task createTask(String title, long creator, LocalDateTime dueDate, TaskType type, Map<String, Object> metadata) {

        Task task;
        if(type.equals(TaskType.Bug)) {
            task =  new Bug(taskIdCounter++, title, creator, dueDate, metadata);
        } else if(type.equals(TaskType.Feature)) {
            task = new Feature(taskIdCounter++, title, creator, dueDate, metadata);
        } else if(type.equals(TaskType.Story)) {
            task = new Story(taskIdCounter++, title, creator, dueDate, metadata);
        } else if(type.equals(TaskType.Subtrack)) {
            task = new Subtrack(taskIdCounter++, title, creator, dueDate, metadata);
        } else {
            throw new TaskPlannerException("Unsupported task type:" + type);
        }
        tasks.add(task);
        return task;
    }

    @Override
    public void changeTaskStatus(long id, TaskStatus status) {

        Task task = getTaskById(id)
                .orElseThrow(() -> new TaskPlannerException("Task not found with taskId: "+id));
        task.changeStatus(status);
    }

    @Override
    public void changeTaskAsignee(long id, long asigneeId) {

        Task task = getTaskById(id)
                .orElseThrow(() -> new TaskPlannerException("Task not found with taskId: "+id));
        task.setAssignee(asigneeId);
    }

    @Override
    public Optional<Task> getTaskById(long taskId) {
        return tasks.stream().filter(task -> task.getId()==taskId).findFirst();
    }
}
