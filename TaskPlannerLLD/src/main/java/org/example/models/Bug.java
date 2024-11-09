package org.example.models;

import org.example.exceptions.TaskPlannerException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class Bug extends Task {

    private BugSeverity severity;

    public Bug(long id, String title, long creator, LocalDateTime dueDate, Map<String,Object> metadata) {

        this.validateInput(id, title, creator, dueDate, metadata);
        this.id = id;
        this.title = title;
        this.creator = creator;
        changeStatus(TaskStatus.OPEN);
        this.type = TaskType.Bug;
        this.dueDate = dueDate;
        this.metadata = metadata;
        this.severity = BugSeverity.valueOf(metadata.get("severity").toString());
    }
    @Override
    protected void validateMetadata(Map<String,Object> metadata) {
        if(!metadata.containsKey("severity"))
            throw new TaskPlannerException("Please provide severity for this bug");
    }

    private void validateStatus(TaskStatus status) {

        if(!(status.equals(TaskStatus.OPEN) || status.equals(TaskStatus.IN_PROGRESS) || status.equals(TaskStatus.FIXED))) {
            throw new TaskPlannerException("Bug can only have status among OPEN,IN_PROGRESS,FIXED");
        }
    }

    @Override
    public void changeStatus(TaskStatus newStatus) {

        validateStatus(newStatus);
        this.status = newStatus;
    }
}
