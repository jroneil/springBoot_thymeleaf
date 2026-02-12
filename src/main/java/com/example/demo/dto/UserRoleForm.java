package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class UserRoleForm {

    @NotNull(message = "Select a user")
    private Long userId;

    @NotNull(message = "Select at least one role")
    @Size(min = 1, message = "Select at least one role")
    private List<Long> assignedRoleIds;

    public UserRoleForm() {
    }

    public UserRoleForm(Long userId, List<Long> assignedRoleIds) {
        this.userId = userId;
        this.assignedRoleIds = assignedRoleIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getAssignedRoleIds() {
        return assignedRoleIds;
    }

    public void setAssignedRoleIds(List<Long> assignedRoleIds) {
        this.assignedRoleIds = assignedRoleIds;
    }
}
