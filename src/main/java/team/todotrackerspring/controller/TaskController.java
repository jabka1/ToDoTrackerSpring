package team.todotrackerspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.todotrackerspring.model.User;
import team.todotrackerspring.service.TaskService;
import team.todotrackerspring.service.UserService;

import java.time.LocalDate;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public String createTask( @RequestParam String name, @RequestParam String description, @RequestParam String deadline,
            @RequestParam int priority, @RequestParam String sortBy) {
        User user = userService.getCurrentUser();
        taskService.createTask(user, name, description, LocalDate.parse(deadline), priority);
        return "redirect:/home?sortBy=" + sortBy;
    }

    @PostMapping("/markCompleted")
    public String markTaskAsCompleted(@RequestParam Long taskId, @RequestParam String sortBy) {
        taskService.markTaskAsCompleted(taskId);
        return "redirect:/home?sortBy=" + sortBy;
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, @RequestParam String sortBy) {
        taskService.deleteTask(id);
        return "redirect:/home?sortBy=" + sortBy;
    }

}
