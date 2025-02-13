package team.todotrackerspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import team.todotrackerspring.decorator.sorting.*;
import team.todotrackerspring.model.User;
import team.todotrackerspring.service.TaskService;
import team.todotrackerspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TaskService taskService;

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/register";
        }

        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            redirectAttributes.addFlashAttribute("error", "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long.");
            return "redirect:/register";
        }

        if (userService.isUsernameTaken(username)) {
            redirectAttributes.addFlashAttribute("error", "User already exists.");
            return "redirect:/register";
        }

        User user = userService.registerUser(username, password);
        redirectAttributes.addFlashAttribute("message", "User registered successfully. Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "none") String sortBy, Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("sortBy", sortBy);

        TaskSortingDecorator sortingDecorator = getSortingDecorator(sortBy);
        model.addAttribute("tasks", taskService.getAllTasksSorted(user.getUsername(), sortingDecorator));

        return "home";
    }

    private TaskSortingDecorator getSortingDecorator(String sortBy) {
        return switch (sortBy) {
            case "name" -> new SortByNameDecorator();
            case "deadline" -> new SortByDeadlineDecorator();
            case "priority" -> new SortByPriorityDecorator();
            case "completed" -> new SortByCompletionDecorator();
            default -> null;
        };
    }

}
