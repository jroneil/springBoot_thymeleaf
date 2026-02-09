package com.example.demo.model;

/**
 * Simple POJO representing a user profile.
 */
public record UserProfile(
    String username,
    String email,
    String department
) {}
