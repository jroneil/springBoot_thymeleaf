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
- `src/main/resources/templates/template.html`: A blank starter template for new pages.
- `src/main/resources/templates/*.html`: Page templates (`home`, `roles`) that call the layout.
- `src/main/resources/static/css/app.css`: The styling for the entire application.

## Blank Page Template
This project includes a **Blank Page Template** (`/new-page`) designed to be a starting point for additional pages. 
- **Controller**: `TemplateController.java`
- **Template**: `template.html`
- **Usage**: Copy the controller method and the `template.html` file to quickly spin up new pages while maintaining the consistent layout and styling.

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
Each page uses the following pattern to wrap its content and inject page-specific metadata:

```html
<div th:replace="~{layout :: layout(~{::content}, ${pageTitle}, ~{::head})}">
    <th:block th:fragment="head">
        <!-- Page specific tags (styles/scripts) -->
    </th:block>
    <div th:fragment="content">
        <!-- Page specific content here -->
    </div>
</div>
```

### Layout Definition
The `layout.html` file defines the shell and supports active link highlighting via the `activeModule` model attribute:

```html
<div th:fragment="layout(content, pageTitle, extraStatic)">
    <th:block th:replace="${extraStatic}"/>
    <div th:replace="~{fragments/header :: header}"></div>
    ...
</div>
```

## Professional Enhancements Added
- **Active Navigation**: Links in the header are automatically highlighted based on the `activeModule` attribute passed from the controller.
- **Dynamic Head Injection**: Individual templates can now inject their own CSS or JS into the global layout's `<head>` section via a dedicated fragment.
