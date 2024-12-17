package team.todotrackerspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.todotrackerspring.decorator.CompletedTaskDecorator;
import team.todotrackerspring.decorator.DeadlineTaskDecorator;
import team.todotrackerspring.decorator.PriorityTaskDecorator;
import team.todotrackerspring.decorator.TaskDecorator;
import team.todotrackerspring.decorator.sorting.TaskSortingDecorator;
import team.todotrackerspring.model.Task;
import team.todotrackerspring.model.User;
import team.todotrackerspring.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /*public List<Task> getAllTasks(String username) {
        return taskRepository.findByUser_Username(username);
    }*/

    public List<Task> getAllTasksSorted(String username, TaskSortingDecorator sortingDecorator) {
        List<Task> tasks = taskRepository.findByUser_Username(username);

        if (sortingDecorator != null) {
            tasks = sortingDecorator.sort(tasks);
        }

        return tasks;
    }

    public void createTask(User user, String name, String description, LocalDate deadline, int priority) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setUser(user);

        TaskDecorator deadlineDecorator = new DeadlineTaskDecorator(deadline);
        TaskDecorator priorityDecorator = new PriorityTaskDecorator(priority);
        TaskDecorator completedDecorator = new CompletedTaskDecorator(false);
        deadlineDecorator.decorate(task);
        priorityDecorator.decorate(task);
        completedDecorator.decorate(task);

        taskRepository.save(task);
    }

    public void markTaskAsCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        TaskDecorator completedDecorator = new CompletedTaskDecorator(true);
        completedDecorator.decorate(task);

        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
