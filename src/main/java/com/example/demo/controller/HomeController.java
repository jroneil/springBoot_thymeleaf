package com.example.demo.controller;

import com.example.demo.model.UserProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Page title for the layout
        model.addAttribute("pageTitle", "Home");

        // Single object: current user profile
        UserProfile currentUser = new UserProfile(
            "jdoe",
            "john.doe@example.com",
            "Engineering"
        );
        model.addAttribute("currentUser", currentUser);

        // List of recent activities
        List<String> recentActivities = List.of(
            "Logged in from New York, NY",
            "Updated profile settings",
            "Completed security training",
            "Reviewed Q4 reports",
            "Joined the DevOps team channel"
        );
        model.addAttribute("recentActivities", recentActivities);

        return "home";
    }
}
