# Personal Resume Website API

This project is a Spring Boot-based app for managing a personal resume website. It was inspired by the static HTML and CSS resume found in the [Thomashighbaugh's repository](https://github.com/Thomashighbaugh/resume).

## Project Overview

1.  **Inspiration**: Based on a static HTML and CSS [resume](https://resume-thomas-leon-highbaugh.vercel.app/) .
2.  **Conversion to Thymeleaf Template**:
    *   Replaced static content with Thymeleaf placeholders.
    *   Integrated with Spring Boot to dynamically populate resume data from the database.
3.  **API Development**:
    *   Created RESTful API endpoints using Spring Boot for managing a single comprehensive Resume object.
    *   Education, Experience, and Skills are modeled as collections embedded within the Resume entity using JPA `@Embeddable` and `@ElementCollection`.
    *   Implemented CRUD operations for the Resume via `ResumeService` and `ResumeRepository`. Utilizes Spring Data JPA and Lombok for reducing boilerplate code.
4.  **Database Setup**:
    *   Configured PostgreSQL for data storage.
    *   Dockerized PostgreSQL for easy management and consistency across environments.
    *   Includes `UserDataInitializer.java` to create a default application user on startup if one doesn't exist, using environment variables for credentials. Schema creation is typically handled by Spring Boot's JPA (`spring.jpa.hibernate.ddl-auto`).
5.  **Interactive Documentation**:
    *   Integrated Swagger UI for interactive API documentation.
6.  **Error Handling and Validation**:
    *   Implemented global exception handling with `@ControllerAdvice`.
    *   Added data validation.
7.  **Security Authentication**:
    *   Basic authentication for the API.
    *   Configured security to allow public access to GET endpoints and restrict POST, PUT, and DELETE endpoints to authenticated users.
    *   Utilized Spring Security and implemented `MyUserDetailsService` for user authentication.
    *   Secured password storage with a password encoder.
8.  **Continuous integration and Continuous Delivery CI/CD**
    *   CI/CD deployment with GitHub and Docker Hub.
    *   Automated infrastructure provisioning and configuration management for Hetzner Cloud using Terraform and Ansible.

---

## Technologies Used

-   **Spring Boot**: For building API endpoints and handling requests/responses.
-   **PostgreSQL**: Relational database for managing resume data.
-   **Lombok**: For reducing boilerplate code with annotations.
-   **Thymeleaf**: Template engine for rendering web pages.
-   **Swagger UI**: For interactive API documentation.
-   **Docker**: For containerizing PostgreSQL (local dev) and the application itself.
-   **Docker Compose**: For orchestrating local development environment and production deployment.
-   **Spring Security**: For securing API endpoints and managing user authentication.
-   **GitHub Actions**: For Continuous Integration (CI) and Docker image building/pushing.
-   **Terraform**: For Infrastructure as Code (IaC) to provision cloud resources on Hetzner.
-   **Ansible**: For Configuration Management to set up the server and deploy the application.

---

## Features

-   **CRUD Operations**: Manage a comprehensive Resume object including embedded lists for educational experiences, work experiences, and skills.
-   **Interactive Documentation**: Explore and test API endpoints with Swagger UI.
-   **Modular Structure**: Clean and scalable project structure.
-   **Security Authentication**: Secure API endpoints with basic authentication.
-   **Automated Deployment**: Full end-to-end automated deployment to Hetzner Cloud.
-   **Initial User Creation**: Automatic creation of a default application admin user on first run via `UserDataInitializer.java`.

---

## Deployment to Hetzner Cloud

This project is configured for automated deployment to a Hetzner Cloud VPS using a combination of Terraform and Ansible.

*   **Terraform**: Provisions the cloud server and its basic networking and security.
*   **Ansible**: Configures the server, installs Docker and Nginx, and deploys the application containers.

For a complete, step-by-step guide on how to deploy the application from scratch, please see the detailed **[Hetzner Deployment Guide](hetzner-deployment.md)**.

---

## Installation & Local Development

There are two main ways to run the application locally:

**1. Running Directly with Maven (Requires Local PostgreSQL):**

Follow these steps if you have PostgreSQL installed and running directly on your machine:

1.  Clone the repository:
    ```bash
    git clone https://github.com/AngelosGi/personal-website.git
    # Or use SSH
    git clone git@github.com:AngelosGi/personal-website.git
    ```

2.  Install dependencies:
    ```bash
    mvn install
    ```

3.  **Database Setup**:
    *   Ensure a PostgreSQL database is set up and running.
    *   Update the database connection details in `application.properties`.
    *   The `UserDataInitializer.java` class will attempt to create an initial user. For this to work when running with Maven directly, you would typically set the required environment variables (`APP_INITIAL_USER_USERNAME`, `APP_INITIAL_USER_PASSWORD`) in your IDE's run configuration or your shell before starting the application. Ensure the `app_user` table exists in your database (Spring Boot with `spring.jpa.hibernate.ddl-auto=update` in `application.properties` might create it, or you can create it manually if `ddl-auto` is not set to `create` or `update`).

4.  Start the Spring Boot application:
    ```bash
    mvn spring-boot:run
    # Or just press the play button in your IDE
    ```

5.  Access the API at `http://localhost:8080/api/...` in your browser or API client.
6.  Access the resume template at `http://localhost:8080/resume/{id}`.

**2. Running with Docker Compose (Recommended for Isolated Environment):**

This method uses Docker Compose to run both the application (using the image from Docker Hub) and a PostgreSQL database in containers. The `UserDataInitializer.java` class within the application is responsible for creating the initial admin user based on environment variables. Spring Boot's JPA/Hibernate capabilities (e.g., `spring.jpa.hibernate.ddl-auto=update` in `application.properties` if not overridden by Docker environment variables) will handle table creation if the tables do not already exist in the database volume.

1.  **Prerequisites**: Ensure Docker and Docker Compose are installed.
2.  **Clone Repository**: Clone the project if you haven't already.
3.  **Configure Environment**:
    *   Copy `.example.env` to `.env`:
        ```bash
        cp .example.env .env
        ```
    *   Edit the new `.env` file and fill in your actual `POSTGRES_PASSWORD`, `DOCKERHUB_USERNAME` (if different), and `APP_INITIAL_USER_PASSWORD`. These credentials will be used by `UserDataInitializer.java`.
        ```dotenv
        # .env (copied from .example.env)
        POSTGRES_USER=Anggi
        POSTGRES_PASSWORD=your_strong_db_password # Replace
        POSTGRES_DB=postgres
        DB_SERVICE_NAME=db
        DB_PORT=5432
        DOCKERHUB_USERNAME=anggian

        APP_INITIAL_USER_USERNAME=anggi
        APP_INITIAL_USER_PASSWORD=your_strong_app_admin_password # Replace
        ```
    *   **Important**: The `.env` file is already listed in `.gitignore`, so your secrets won't be committed.
4.  **Run Docker Compose**:
    ```bash
    docker-compose up -d
    ```
    The PostgreSQL container will start. The Spring Boot application, once started, will connect to the database. If tables are missing and `spring.jpa.hibernate.ddl-auto` is appropriately configured (e.g., to `update` or `create`), Spring Boot will attempt to create them. Then, `UserDataInitializer.java` will use the credentials from `.env` to create the initial 'anggi' user in the `app_user` table if it doesn't exist.
    *(Note: The `init.sql` file in the project root is also mounted into the PostgreSQL container by `docker-compose.yml`. If it contains `CREATE TABLE` statements, it can ensure tables exist before the application starts. If `UserDataInitializer.java` is robustly handling user creation, `init.sql` might only be needed for schema or other non-application-user data.)*
5.  **Access Application**: Wait a moment for the containers to start (check logs with `docker-compose logs -f app`), then access the application at `http://localhost:8080`.
6.  **Stop Services**: When finished, stop and remove the containers:
    ```bash
    docker-compose down
    ```
    To also remove the database data volume (and thus reset the database for the next `up`), use `docker-compose down -v`.

## Usage

-   Access the Swagger documentation at `http://localhost:8080/swagger-ui/index.html` to explore and interact with the API endpoints.
-   Use tools like Postman to make requests to the API endpoints.
-   View the rendered HTML resume at `http://localhost:8080/resume/{id}` (replace `{id}` with a valid resume ID).

## API Endpoints

The API provides the following endpoints under the `/api` base path:

*   **`GET /api/all`**: Retrieves a list of all resumes. (Returns `List<ResumeDTO>`)
*   **`GET /api/{id}`**: Retrieves a specific resume by its ID. (Returns `ResumeDTO`)
*   **`POST /api/add`**: Creates a new resume. Requires authentication. Expects a `ResumeDTO` in the request body. (Returns the created `ResumeDTO`)
*   **`PUT /api/edit/{id}`**: Updates an existing resume identified by its ID. Requires authentication. Expects a `ResumeDTO` in the request body. (Returns the updated `ResumeDTO`)
*   **`DELETE /api/delete/{id}`**: Deletes a specific resume by its ID. Requires authentication. (Returns no content)

**Note:** The `ResumeDTO` includes embedded lists for `educationEntries`, `experienceEntries`, and `skills`.

---

### To-Do

-   ~~Auth~~ Done
-   ~~Hide db credentials for production.~~ Done (using environment variables and Ansible Vault)
-   ~~Add skills from db~~ Done
-   ~~Dockerize app~~ Done (Dockerfile created)
-   ~~CI Pipeline (Build/Test/Auto-Merge)~~ Done (GitHub Actions)
-   ~~CI Pipeline (Docker Build/Push to Docker Hub)~~ Done (GitHub Actions)
-   ~~Deploy to Cloud (Hetzner)~~ Done (Terraform & Ansible, documented in `hetzner-deployment.md`)
-   Add more resume templates

---

## CI/CD Pipeline (GitHub Actions)

This project uses GitHub Actions (`.github/workflows/ci-cd.yml`) for Continuous Integration:

1.  **Triggers**:
    *   On pushes to `feature/*` or `fix/*` branches.
    *   On pushes to the `master` branch.
    *   Manual trigger (`workflow_dispatch`).
2.  **Jobs**:
    *   **`build`**: Compiles the Java code and packages the application (`mvn clean package -DskipTests`). Uploads the JAR as an artifact.
    *   **`test`**: Runs the unit tests (`mvn test`). Depends on `build`.
    *   **`auto_merge_to_develop`**: If triggered by a `feature/*` or `fix/*` push and `test` succeeds, automatically merges the branch into `develop`.
    *   **`docker_build_push`**: If triggered by a `master` push and `test` succeeds, builds a Docker image using the project `Dockerfile` and pushes it to Docker Hub (`anggian/personal-website`) tagged with `latest` and the short commit SHA. Requires `DOCKERHUB_USERNAME` and `DOCKERHUB_TOKEN` secrets in the GitHub repository settings.

---
