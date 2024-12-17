package team.todotrackerspring.decorator.sorting;

import team.todotrackerspring.model.Task;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByNameDecorator implements TaskSortingDecorator {

    @Override
    public List<Task> sort(List<Task> tasks) {
        Collections.sort(tasks, Comparator.comparing(Task::getName));
        return tasks;
    }
}
