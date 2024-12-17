package team.todotrackerspring.decorator;

import team.todotrackerspring.model.Task;

import java.time.LocalDate;

public class DeadlineTaskDecorator implements TaskDecorator {

    private final LocalDate deadline;

    public DeadlineTaskDecorator(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public Task decorate(Task task) {
        task.setDeadline(deadline);
        return task;
    }
}
