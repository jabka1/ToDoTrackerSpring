package team.todotrackerspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping
    public String listTasks(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("tasks", taskService.getAllTasks(user.getUsername()));
        model.addAttribute("today", LocalDate.now());
        return "task/list";
    }


    @PostMapping("/create")
    public String createTask(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String deadline,
            @RequestParam int priority) {
        User user = userService.getCurrentUser();
        taskService.createTask(user, name, description, LocalDate.parse(deadline), priority);
        return "redirect:/home";
    }

    @PostMapping("/markCompleted")
    public String markTaskAsCompleted(@RequestParam Long taskId) {
        taskService.markTaskAsCompleted(taskId);
        return "redirect:/home";
    }

}
