package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public class UserManageForm {

    @NotNull(message = "Select a user")
    private Long userId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 80, message = "Display name must be between 2 and 80 characters")
    private String displayName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$", message = "Email must contain a valid domain and TLD (e.g. .com, .org)")
    @Size(max = 120, message = "Email must be at most 120 characters")
    private String email;

    @Size(max = 5, message = "International part too long")
    private String countryCode;

    @Size(max = 20, message = "Phone number too long")
    @Pattern(regexp = "^[0-9\\-\\s\\(\\)]*$", message = "Phone number can only contain digits, spaces, hyphens, and parentheses")
    private String phoneNumber;

    @Size(max = 10, message = "Extension too long")
    private String extension;

    @Size(min = 1, message = "Select at least one role")
    private List<Long> assignedRoleIds;

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<Long> getAssignedRoleIds() {
        return assignedRoleIds;
    }

    public void setAssignedRoleIds(List<Long> assignedRoleIds) {
        this.assignedRoleIds = assignedRoleIds;
    }
}
