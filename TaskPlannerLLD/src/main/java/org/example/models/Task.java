package org.example.models;

import org.example.exceptions.TaskPlannerException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public abstract class Task {

    protected long id;
    protected long sprintId;
    protected String title;
    protected long creator;
    protected Optional<Long> assignee;
    protected TaskStatus status;
    protected TaskType type;
    protected LocalDateTime dueDate;
    protected Map<String,Object> metadata;

    public long getId() {
        return this.id;
    }

    public void setSprintId(long sprintId) {
        this.sprintId = sprintId;
    }

    public long getSprintId() {
        return this.sprintId;
    }

    public LocalDateTime getDueDate() { return this.dueDate; }

    public TaskStatus getStatus() { return this.getStatus(); }

    public Optional<Long> getAssignee() {
        return this.assignee;
    }

    public TaskType getTasktype() {
        return this.type;
    }

    public void setAssignee(long asigneeId) {
        this.assignee = Optional.of(asigneeId);
    }

    protected void validateInput(long id, String title, long creator, LocalDateTime dueDate, Map<String,Object> metadata) {

        if(id<=0) {
            throw new TaskPlannerException("TaskId cant be empty");
        }
        if(title.isEmpty()) {
            throw new TaskPlannerException("Title cant be empty");
        }
        if(creator<=0) {
            throw new TaskPlannerException("Creator cant be empty");
        }
        if(dueDate==null) {
            throw new TaskPlannerException("Duedate cant be empty");
        }
        validateMetadata(metadata);
    }

    protected abstract void validateMetadata(Map<String,Object> metadata);

    public abstract void changeStatus(TaskStatus newStatus);
}
