package com.example.demo.model;

/**
 * Simple POJO representing a role with permissions.
 */
public record Role(
    String name,
    String description,
    int permissionCount
) {}
