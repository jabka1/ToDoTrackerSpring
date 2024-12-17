package team.todotrackerspring.decorator;

import team.todotrackerspring.model.Task;

public interface TaskDecorator {
    Task decorate(Task task);
}
