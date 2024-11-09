package org.example.services.sprintservice;

import org.example.exceptions.TaskPlannerException;
import org.example.models.Sprint;
import org.example.models.Task;
import org.example.services.taskservice.TaskService;
import org.example.services.taskservice.TaskServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SprintServiceImpl implements SprintService {

    private SprintServiceImpl() {}
    private static SprintServiceImpl sprintService = null;
    private List<Sprint> sprints = new ArrayList<>();

    public static SprintServiceImpl getInstance() {
        if(sprintService==null) {
            synchronized (SprintServiceImpl.class) {
                if(sprintService==null) {
                    sprintService = new SprintServiceImpl();
                }
            }
        }
        return sprintService;
    }

    private static long sprintIdCounter = 1;
    @Override
    public Sprint createSprint() {
        Sprint sprint = new Sprint(sprintIdCounter++);
        sprints.add(sprint);
        return sprint;
    }

    @Override
    public Optional<Sprint> getSprintById(long id) {
        return sprints.stream().filter(sprint -> sprint.getId()==id).findFirst();
    }

    @Override
    public TaskPlannerException deleteSprint(long sprintId) {
        return null;
    }
}
