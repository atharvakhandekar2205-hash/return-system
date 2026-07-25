package com.example.returnsystem.controller;

import com.example.returnsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthPageController {

    private final UserService userService;

    public AuthPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {

        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully");
        }

        if (registered != null) {
            model.addAttribute("message", "Registration successful! Please login");
        }

        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/do-register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {

        String result = userService.registerUser(email, password);

        if ("EMAIL_EXISTS".equals(result)) {
            model.addAttribute("error", "Email already registered");
            return "signup";
        }

        return "redirect:/login?registered=true";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }
}