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
        CommandLineRunner initDatabase(RoleRepository roleRepository,
                        com.example.demo.repository.UserRepository userRepository) {
                return args -> {
                        // Seed Roles
                        if (roleRepository.count() == 0) {
                                roleRepository.saveAll(List.of(
                                                new Role("ADMIN", "Administrator", "Full system access"),
                                                new Role("EDITOR", "Editor", "Can edit and publish content"),
                                                new Role("VIEWER", "Viewer", "Read-only access"),
                                                new Role("AUDITOR", "Auditor", "Can view audit logs"),
                                                new Role("DEV", "Developer", "Technical support access")));
                        }

                        // Seed Users
                        if (userRepository.count() == 0) {
                                Role adminRole = roleRepository.findAll().stream()
                                                .filter(r -> r.getCode().equals("ADMIN")).findFirst()
                                                .orElseThrow();
                                Role viewerRole = roleRepository.findAll().stream()
                                                .filter(r -> r.getCode().equals("VIEWER"))
                                                .findFirst().orElseThrow();

                                com.example.demo.model.User alice = new com.example.demo.model.User("alice",
                                                "Alice Johnson", "alice@example.com", "+1", "555-0100", "123");
                                alice.addRole(adminRole);
                                alice.addRole(viewerRole);

                                com.example.demo.model.User bob = new com.example.demo.model.User("bob", "Bob Smith",
                                                "bob@example.com", "+1", "555-0200", null);
                                bob.addRole(viewerRole);

                                userRepository.saveAll(List.of(alice, bob));
                        }
                };
        }
}
