package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RoleRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Role("ADMIN", "Administrator", "Full system access"),
                        new Role("EDITOR", "Editor", "Can edit and publish content"),
                        new Role("VIEWER", "Viewer", "Read-only access")));
            }
        };
    }
}
