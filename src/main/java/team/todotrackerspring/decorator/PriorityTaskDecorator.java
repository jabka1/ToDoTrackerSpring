package team.todotrackerspring.decorator;

import team.todotrackerspring.model.Task;

public class PriorityTaskDecorator implements TaskDecorator {

    private final int priority;

    public PriorityTaskDecorator(int priority) {
        if (priority < 1 || priority > 3) {
            throw new IllegalArgumentException("Priority must be between 1 and 3.");
        }
        this.priority = priority;
    }

    @Override
    public Task decorate(Task task) {
        task.setPriority(priority);
        return task;
    }
}
