package team.todotrackerspring.decorator.sorting;

import team.todotrackerspring.model.Task;

import java.util.List;

public interface TaskSortingDecorator {
    List<Task> sort(List<Task> tasks);
}
