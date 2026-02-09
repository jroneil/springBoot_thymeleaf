# Spring Boot 3.5 + Thymeleaf Layout Demo

This project demonstrates a professional, reusable layout pattern in Spring Boot 3.5.0 using Thymeleaf fragments. It features a dark-themed, responsive UI with a parameterized layout system.

## Key Features
- **Spring Boot 3.5.0**: Using the latest features and dependency management.
- **Thymeleaf Layout Pattern**: A reusable `layout.html` that handles the shell (header, footer, meta) and accepts page-specific content as fragments.
- **Parameterized Fragments**: The layout accepts both a `content` fragment and a `pageTitle` string, ensuring clean separation of concerns.
- **Modern Dark UI**: Custom CSS with glassmorphism, responsive tables, and polished typography.
- **No Database**: Uses in-memory data for quick demonstration.

## Project Structure
- `src/main/resources/templates/layout.html`: The master layout defining the fragment `layout(content, pageTitle)`.
- `src/main/resources/templates/fragments/`: Contains `header.html` and `footer.html`.
- `src/main/resources/templates/*.html`: Page templates (`home`, `roles`) that call the layout.
- `src/main/resources/static/css/app.css`: The styling for the entire application.

## Prerequisites
- **Java 17+**
- **Maven 3.x**

## How to Run

1.  Open your terminal in the project root directory.
2.  Run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```
3.  Once the application starts, access it at the following URLs:
    - **Home Page**: [http://localhost:8080/](http://localhost:8080/)
    - **Roles Page**: [http://localhost:8080/roles](http://localhost:8080/roles)

## Implementation Details

### Reusable Layout Call
Each page uses the following pattern to wrap its content in the global layout:

```html
<div th:replace="~{layout :: layout(~{::content}, ${pageTitle})}">
    <div th:fragment="content">
        <!-- Page specific content here -->
    </div>
</div>
```

### Layout Definition
The `layout.html` file defines the shell:

```html
<div th:fragment="layout(content, pageTitle)">
    <div th:replace="~{fragments/header :: header}"></div>
    <main>
        <h1 th:text="${pageTitle}"></h1>
        <div th:replace="${content}"></div>
    </main>
    <div th:replace="~{fragments/footer :: footer}"></div>
</div>
```
