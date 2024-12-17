package team.todotrackerspring.decorator;

import team.todotrackerspring.model.Task;

public class CompletedTaskDecorator implements TaskDecorator {

    private final boolean completed;

    public CompletedTaskDecorator(boolean completed) {
        this.completed = completed;
    }

    @Override
    public Task decorate(Task task) {
        task.setCompleted(completed);
        return task;
    }
}
