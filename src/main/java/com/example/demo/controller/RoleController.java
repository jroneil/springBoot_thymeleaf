package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "Roles");
        model.addAttribute("activeModule", "roles");
        model.addAttribute("roles", roleRepository.findAll());
        return "roles";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Create Role");
        model.addAttribute("activeModule", "roles");
        model.addAttribute("role", new Role());
        return "role-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        model.addAttribute("pageTitle", "Edit Role");
        model.addAttribute("activeModule", "roles");
        model.addAttribute("role", role);
        return "role-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Role role, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", role.getId() == null ? "Create Role" : "Edit Role");
            model.addAttribute("activeModule", "roles");
            return "role-form";
        }
        roleRepository.save(role);
        redirectAttributes.addFlashAttribute("successMessage", "Role saved successfully!");
        return "redirect:/roles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roleRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Role deleted successfully!");
        return "redirect:/roles";
    }
}
