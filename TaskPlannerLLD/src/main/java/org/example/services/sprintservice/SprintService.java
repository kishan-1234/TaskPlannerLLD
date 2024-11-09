package org.example.services.sprintservice;

import org.example.exceptions.TaskPlannerException;
import org.example.models.Sprint;
import org.example.models.Task;

import java.util.Optional;

public interface SprintService {
    public Sprint createSprint();
    public Optional<Sprint> getSprintById(long id);
    public TaskPlannerException deleteSprint(long sprintId);
}
