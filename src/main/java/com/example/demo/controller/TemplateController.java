package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * A starter controller for new pages.
 * Copy this class when creating a new feature area.
 */
@Controller
public class TemplateController {

    @GetMapping("/new-page")
    public String newPage(Model model) {
        // 1. Set the page title (used by layout.html)
        model.addAttribute("pageTitle", "New Template Page");

        // 2. Set active navigation module
        model.addAttribute("activeModule", "template");

        // 3. Add your data objects
        SampleData data = new SampleData("Example Item", "A brief description here");
        model.addAttribute("exampleObject", data);

        // 3. Add your lists
        List<String> items = List.of("Feature A", "Feature B", "Feature C");
        model.addAttribute("exampleList", items);

        // 4. Return the name of the template file
        // (src/main/resources/templates/template.html)
        return "template";
    }

    /**
     * Simple inner record for demonstration.
     */
    public record SampleData(String name, String description) {
    }
}
