package com.example.demo.dto;

/**
 * ViewModel for displaying user rows in the search/list page.
 * Includes role count to avoid N+1 queries.
 */
public class UserRowVM {

    private Long id;
    private String username;
    private String displayName;
    private String email;
    private int roleCount;

    public UserRowVM() {
    }

    public UserRowVM(Long id, String username, String displayName, String email, int roleCount) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.roleCount = roleCount;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getRoleCount() {
        return roleCount;
    }

    public void setRoleCount(int roleCount) {
        this.roleCount = roleCount;
    }
}
