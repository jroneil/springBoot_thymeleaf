package com.example.demo.controller;

import com.example.demo.dto.UserManageForm;
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
@RequestMapping("/users/manage")
public class UserManageController {

    private final UserService userService;

    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String manageUser(@RequestParam(required = false) Long userId, Model model,
            RedirectAttributes redirectAttributes) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);

        User selectedUser = null;
        if (userId != null) {
            try {
                selectedUser = userService.getUser(userId);
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("flashError", "User not found");
                return "redirect:/users";
            }
        } else if (!users.isEmpty()) {
            selectedUser = users.get(0);
        }

        model.addAttribute("selectedUser", selectedUser);
        model.addAttribute("activeModule", "users");

        if (selectedUser != null) {
            UserManageForm form = new UserManageForm();
            form.setUserId(selectedUser.getId());
            form.setUsername(selectedUser.getUsername());
            form.setDisplayName(selectedUser.getDisplayName());
            form.setEmail(selectedUser.getEmail());
            form.setCountryCode(selectedUser.getCountryCode());
            form.setPhoneNumber(selectedUser.getPhoneNumber());
            form.setExtension(selectedUser.getExtension());
            form.setAssignedRoleIds(selectedUser.getRoles().stream().map(Role::getId).collect(Collectors.toList()));

            model.addAttribute("userManageForm", form);
            populateRolesModel(selectedUser.getRoles().stream().map(Role::getId).collect(Collectors.toList()), model);
        }

        return "users-manage";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("userManageForm") UserManageForm form, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        // Business Rule: Username uniqueness check
        if (!result.hasFieldErrors("username")) {
            if (userService.isUsernameTaken(form.getUsername(), form.getUserId())) {
                result.rejectValue("username", "duplicate", "This username is already taken");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("users", userService.listUsers());
            model.addAttribute("selectedUser", userService.getUser(form.getUserId()));
            model.addAttribute("activeModule", "users");
            populateRolesModel(form.getAssignedRoleIds(), model);
            return "users-manage";
        }

        userService.updateUser(form);
        redirectAttributes.addFlashAttribute("successMessage", "User '" + form.getUsername()
                + "' updated successfully with " + form.getAssignedRoleIds().size() + " roles.");

        return "redirect:/users/manage?userId=" + form.getUserId();
    }

    @PostMapping("/clear-roles")
    public String clearRoles(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        userService.clearUserRoles(userId);
        redirectAttributes.addFlashAttribute("successMessage", "All roles cleared for user.");
        return "redirect:/users/manage?userId=" + userId;
    }

    private void populateRolesModel(List<Long> assignedRoleIds, Model model) {
        List<Role> allRoles = userService.listRoles();
        List<Long> safeAssignedRoleIds = (assignedRoleIds != null) ? assignedRoleIds : new ArrayList<>();

        List<Role> assignedRoles = allRoles.stream()
                .filter(r -> safeAssignedRoleIds.contains(r.getId()))
                .sorted(Comparator.comparing(Role::getName))
                .collect(Collectors.toList());

        List<Role> availableRoles = allRoles.stream()
                .filter(r -> !safeAssignedRoleIds.contains(r.getId()))
                .sorted(Comparator.comparing(Role::getName))
                .collect(Collectors.toList());

        model.addAttribute("assignedRoles", assignedRoles);
        model.addAttribute("availableRoles", availableRoles);
    }
}
