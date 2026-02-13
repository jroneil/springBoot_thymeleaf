package com.example.demo.controller;

import com.example.demo.dto.UserRoleForm;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users/roles")
public class UserRoleController {

    private final UserService userService;

    public UserRoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String assignRoles(@RequestParam(required = false) Long userId, Model model) {
        model.addAttribute("pageTitle", "Assign Roles to User");
        model.addAttribute("activeModule", "users");

        List<User> users = userService.listUsers();
        model.addAttribute("users", users);

        User selectedUser = null;
        if (userId != null) {
            selectedUser = userService.getUser(userId);
        } else if (!users.isEmpty()) {
            selectedUser = users.get(0);
        }

        List<Role> allRoles = userService.listRoles();
        List<Role> assignedRoles = new ArrayList<>();
        List<Role> availableRoles = new ArrayList<>(allRoles);

        UserRoleForm form = new UserRoleForm();

        if (selectedUser != null) {
            assignedRoles = new ArrayList<>(selectedUser.getRoles());
            assignedRoles.sort(Comparator.comparing(Role::getName));

            // Remove assigned from available
            // Note: simplistic removeAll relies on equals/hashCode which are default in
            // Entity unless overridden
            // Better to filter by ID for safety
            List<Long> assignedIds = assignedRoles.stream().map(Role::getId).collect(Collectors.toList());
            availableRoles = allRoles.stream()
                    .filter(r -> !assignedIds.contains(r.getId()))
                    .collect(Collectors.toList());

            form.setUserId(selectedUser.getId());
            form.setAssignedRoleIds(assignedIds);
        }

        model.addAttribute("selectedUser", selectedUser);
        model.addAttribute("assignedRoles", assignedRoles);
        model.addAttribute("availableRoles", availableRoles);
        if (!model.containsAttribute("userRoleForm")) {
            model.addAttribute("userRoleForm", form);
        }

        return "users-roles";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("userRoleForm") UserRoleForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Re-populate model data for the view
            model.addAttribute("pageTitle", "Assign Roles to User");
            model.addAttribute("activeModule", "users");

            // Reload users
            model.addAttribute("users", userService.listUsers());

            // Reload user to get name etc
            User selectedUser = null;
            if (form.getUserId() != null) {
                try {
                    selectedUser = userService.getUser(form.getUserId());
                } catch (Exception e) {
                    // if user invalid, maybe deleted
                }
            }
            model.addAttribute("selectedUser", selectedUser);

            // Recompute lists based on SUBMITTED IDs (not DB state) to preserve user
            // selection
            List<Role> allRoles = userService.listRoles();
            List<Long> submittedIds = form.getAssignedRoleIds() != null ? form.getAssignedRoleIds() : new ArrayList<>();

            List<Role> assignedRoles = allRoles.stream()
                    .filter(r -> submittedIds.contains(r.getId()))
                    .sorted(Comparator.comparing(Role::getName))
                    .collect(Collectors.toList());

            List<Role> availableRoles = allRoles.stream()
                    .filter(r -> !submittedIds.contains(r.getId()))
                    .collect(Collectors.toList());

            model.addAttribute("assignedRoles", assignedRoles);
            model.addAttribute("availableRoles", availableRoles);

            return "users-roles";
        }

        userService.updateUserRoles(form.getUserId(), form.getAssignedRoleIds());
        redirectAttributes.addFlashAttribute("successMessage", "Roles updated successfully!");
        return "redirect:/users/roles?userId=" + form.getUserId();
    }

    @PostMapping("/clear")
    public String clear(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        userService.clearUserRoles(userId);
        redirectAttributes.addFlashAttribute("successMessage", "Roles cleared successfully!");
        return "redirect:/users/roles?userId=" + userId;
    }
}
