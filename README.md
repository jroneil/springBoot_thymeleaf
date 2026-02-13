# Spring Boot 3.5.0 + Thymeleaf CRUD Demo

A modern, high-performance CRUD implementation showcasing **Thymeleaf Fragments** for layout management, **Spring Data JPA**, and a **Glassmorphic UI**.

## 📸 Screenshots

### Home Dashboard (**Static**)
*Demonstrates a dashboard rendering static model data within the shared layout.*
![Home Dashboard](screenshots/home.png)

### Roles Management (**Dynamic CRUD**)
*A fully functional CRUD interface backed by an H2 database, featuring validation and JPA persistence.*
![Roles Management](screenshots/roles.png)

### Users Directory (Search & Navigation) (**Dynamic CRUD**)
*A searchable, paginated user directory with case-insensitive filtering and direct navigation to profile management.*
![Users Management](screenshots/users-list.png)

### User Management (Profile + Role Assignment) (**Dynamic CRUD**)
*A unified form for editing user details and assigning roles using a dual listbox, featuring server-side validation, DTO binding, dirty-state detection, and PRG pattern.*
![Users Management](screenshots/users-details.png)

### About Page (**Static**)
*A simple informational page showcasing how static content fits into the fragment-based layout.*
![About Page](screenshots/about.png)

## 🚀 Key Features
- **Strict Theme-less Layouts**: Uses a pure fragment-based system (`layout :: layout`). No extra dialects or XML decorators required.
- **Glassmorphic Design**: A premium dark-mode interface featuring pill-tab navigation, backdrop blurs, and neon accents.
- **H2 Persistence**: Fully functional CRUD with an in-memory H2 database.
- **Robust Validation**: Server-side Bean Validation with real-time error feedback in the UI.
- **Responsive**: Mobile-first design that adapts gracefully to all screen sizes.
- **Dual Listbox UX**: Custom vanilla JS component for intuitive role assignment.
- **Dirty State Tracking**: UX enhancement to warn users of unsaved changes.
- **PRG Pattern**:  Implements Post/Redirect/Get for idempotent form submissions.

## 🛠 Tech Stack
- **Core**: Spring Boot 3.5.0
- **View**: Thymeleaf (Fragment Layouts)
- **Database**: H2 (In-Memory)
- **ORM**: Spring Data JPA / Hibernate
- **Styling**: Vanilla CSS (Modern CSS Variables + Flex/Grid)
- **Typography**: Inter (UI) & Fira Code (Data/Monospace)

## 📱 Navigation & Pages

| Tab | Route | Description |
|-----|-------|-------------|
| **Home** | `/` | Dashboard view with user profile and activity feed. |
| **Roles** | `/roles` | Complete CRUD interface for role management. |
| **Users** | `/users` | **NEW!** Searchable directory with pagination and filtering. |
| **About** | `/about` | Project details and architectural overview. |

## 🔍 User Management Flow (Refactored)

The user management system has been streamlined to use the **Users Directory** as the central entry point.

-   **Searchable List**: Filter users by `username`, `displayName`, or `email` with case-insensitive, partial matching.
-   **Pagination**: Server-side pagination and sorting (`username` ASC by default).
-   **Unified Management**: The "Manage" action leads to a comprehensive form for profile updates and role assignments (Dual Listbox).
-   **Error Handling**: Built-in validation check if a user ID is invalid, with automatic redirection and flash messaging.

## 📂 Project Architecture

### Logic layer
- `RoleController` — Handles CRUD operations and validation.
- `UserRoleController` — Manages complex many-to-many role assignments.
- `UserService` — Orchestrates user and role business logic.
- `HomeController` — Serves dashboard data.
- `AboutController` — Serves static informational content.
- `DataInitializer` — Seeds users (`alice`, `bob`) and roles on boot.

### Data Layer
- `Role` — JPA Entity with `@NotBlank` and unique constraint validation.
- `User` — JPA Entity with `@ManyToMany` relationship to roles.
- `RoleRepository` / `UserRepository` — Data access interfaces.

### Interface Layer
- `layout.html` — The master shell. Owns the `<head>`, CSS, and overall structure.
- `users-roles.html` — Dual listbox interface for assigning relationships.
- `header.html` — Glassmorphic navigation bar with SVG icons.
- `role-form.html` — Reusable form for both creating and editing records.

## 🏃 How to Run

1. **System Requirements**: Java 17+ and Maven.
2. **Launch Application**:
   ```bash
   mvn clean spring-boot:run
   ```
3. **Access UI**: [http://localhost:8080](http://localhost:8080)
4. **Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - **JDBC URL**: `jdbc:h2:mem:roledb`
   - **User**: `sa` / **Password**: `password`

## 🏗 Creating New Pages

This project includes a **Starter Template** (`template.html`) to help you quickly build new pages that inherit the glassmorphic design system.

1.  **Controller**: Set `pageTitle` and `activeModule` in your `Model`.
2.  **HTML**: Copy `template.html`, keeping the `th:replace` call.
3.  **Content**: Place your custom HTML inside the `th:fragment="content"` block.

## 🏗 The Layout Contract
This project uses a strict, non-intrusive layout contract. Pages "wrap" themselves in the layout fragment rather than the layout "decorating" the page:

```html
<!-- In any content page (e.g., home.html) -->
<div th:replace="~{layout :: layout(~{::content}, ${pageTitle})}">
    <div th:fragment="content">
        <!-- Your specific page HTML goes here -->
    </div>
</div>
```

The master `layout.html` then renders the injected page fragment using:
```html
<div th:replace="${content}"></div>
```

---

## 🛠 Technical Gotchas: CSS Not Loading

During development, we encountered a scenario where CSS styles did not render
even though they were correctly linked in `layout.html`.

### Root Cause
When using fragment-based layouts, if the layout fragment only renders a `<div>`,
the final HTML output depends on the *calling template* to provide the
`<html>`, `<head>`, and `<body>` elements.

In our case, the content pages (e.g. `home.html`) acted as the entry point but did
not define their own `<head>`. As a result, the browser rendered a document
without the `<link>` tags and font imports, causing styles to disappear.

### Solution
We adopted a **document-level layout fragment**.

The `layout` fragment in `layout.html` now wraps the **entire HTML document**,
including:

- `<!DOCTYPE html>`
- `<head>` (CSS, fonts, metadata)
- `<body>`

When a page calls:
```html
<div th:replace="~{layout :: layout(...)}"></div>
The page is replaced by a complete, valid HTML document, ensuring that CSS and
fonts are always present in the final output.