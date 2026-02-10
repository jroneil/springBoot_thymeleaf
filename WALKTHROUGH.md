# Walkthrough: Spring Boot Thymeleaf Layout Demo (Enhanced)

This project provides a robust, professional-grade foundation for building Spring Boot applications with a consistent, reusable design system.

## Performance & Architecture
- **Spring Boot 3.5.0**: Leverages the latest Spring framework optimizations.
- **Fragments Pattern**: Uses `th:replace` with parameterized logic to ensure pages remain lightweight and manageable.
- **Modern Styling**: A standard `app.css` providing a premium dark-mode aesthetic with CSS variables for easy customization.

## Professional Features
- **Active Navigation Highlighting**: The layout automatically detects which page you are on and highlights the corresponding link in the header.
- **Page-Specific Head Fragment**: Individual pages can now inject their own `<script>` or `<style>` tags directly into the global `<head>` section without modifying the shared layout file.

## Key Screens

### 1. Home Page
- **URL**: [http://localhost:8080/](http://localhost:8080/)
- **Displays**: User profile information and a dynamic list of activities.
- **Layout Usage**: Demonstrates the base layout and active link highlighting.

### 2. Roles Page
- **URL**: [http://localhost:8080/roles](http://localhost:8080/roles)
- **Displays**: Statistical summary of system roles and a detailed table of permissions.
- **Layout Usage**: Demonstrates a custom `<style>` injection via the `head` fragment to highlight role names.

### 3. Blank Template Page
- **URL**: [http://localhost:8080/new-page](http://localhost:8080/new-page)
- **Purpose**: A "clean slate" for you to copy-paste when creating new pages.
- **Includes**: Sample data binding, list iteration, and instructions on using the `head` fragment.

## How to Verify

1.  **Build**:
    ```bash
    mvn clean compile
    ```
2.  **Run**:
    ```bash
    mvn spring-boot:run
    ```
3.  **Explore**:
    - Visit the dashboard at [localhost:8080/](http://localhost:8080/).
    - Explore role management at [localhost:8080/roles](http://localhost:8080/roles).
    - Use the starter template at [localhost:8080/new-page](http://localhost:8080/new-page).

## Code Quality Check
- [x] Correct Thymeleaf Fragment Passing (Page body renders correctly inside layout).
- [x] No circular includes.
- [x] Parameterized layout (`layout(content, pageTitle, extraStatic)`).
- [x] Active link highlighting (Pro).
- [x] Dynamic `<head>` support (Pro).
- [x] Responsive CSS with modern dark-mode aesthetic.
- [x] 3.5.x Spring Boot Version.
