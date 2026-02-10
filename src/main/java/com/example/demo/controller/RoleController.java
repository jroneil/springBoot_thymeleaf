package com.example.demo.controller;

import com.example.demo.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RoleController {

    @GetMapping("/roles")
    public String roles(Model model) {
        // Page title for the layout
        model.addAttribute("pageTitle", "Roles");

        // Active navigation module
        model.addAttribute("activeModule", "roles");

        // Single object: role summary stats
        RoleSummary summary = new RoleSummary(5, 23);
        model.addAttribute("roleSummary", summary);

        // List of roles
        List<Role> roles = List.of(
                new Role("ADMIN", "Full system access with all permissions", 15),
                new Role("EDITOR", "Can create and modify content", 8),
                new Role("VIEWER", "Read-only access to resources", 3),
                new Role("AUDITOR", "Can view audit logs and reports", 5),
                new Role("GUEST", "Limited temporary access", 2));
        model.addAttribute("roles", roles);

        return "roles";
    }

    /**
     * Simple record for role summary statistics.
     */
    public record RoleSummary(int totalRoles, int totalPermissions) {
    }
}
