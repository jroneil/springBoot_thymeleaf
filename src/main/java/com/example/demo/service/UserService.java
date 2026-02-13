package com.example.demo.service;

import com.example.demo.dto.UserRowVM;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.example.demo.dto.UserManageForm;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll(Sort.by("username"));
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public List<Role> listRoles() {
        return roleRepository.findAll(Sort.by("name"));
    }

    public Set<Role> loadRolesByIds(List<Long> ids) {
        return new HashSet<>(roleRepository.findAllById(ids));
    }

    public void updateUserRoles(Long userId, List<Long> roleIds) {
        User user = getUser(userId);
        Set<Role> roles = loadRolesByIds(roleIds);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateUser(UserManageForm form) {
        User user = getUser(form.getUserId());
        user.setUsername(form.getUsername().trim());
        user.setDisplayName(form.getDisplayName().trim());
        user.setEmail(form.getEmail().trim());
        user.setCountryCode(form.getCountryCode() != null ? form.getCountryCode().trim() : null);
        user.setPhoneNumber(form.getPhoneNumber() != null ? form.getPhoneNumber().trim() : null);
        user.setExtension(form.getExtension() != null ? form.getExtension().trim() : null);

        Set<Role> roles = loadRolesByIds(form.getAssignedRoleIds());
        user.setRoles(roles);

        userRepository.save(user);
    }

    public boolean isUsernameTaken(String username, Long excludeUserId) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username.trim());
        return user.isPresent() && !user.get().getId().equals(excludeUserId);
    }

    public void clearUserRoles(Long userId) {
        User user = getUser(userId);
        user.getRoles().clear();
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Page<UserRowVM> searchUsers(String query, Pageable pageable) {
        Page<User> users;
        if (query == null || query.trim().isEmpty()) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.search(query.trim(), pageable);
        }
        return users.map(this::toUserRowVM);
    }

    private UserRowVM toUserRowVM(User user) {
        return new UserRowVM(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getRoles().size());
    }
}
