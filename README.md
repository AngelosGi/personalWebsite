# Personal Resume Website API

This project is a Spring Boot-based app for managing a personal resume website. It was inspired by the static HTML and CSS resume found in the [Thomashighbaugh's repository](https://github.com/Thomashighbaugh/resume).

## Project Overview

1. **Inspiration**: Based on a static HTML and CSS [resume](https://resume-thomas-leon-highbaugh.vercel.app/) .
2. **Conversion to Thymeleaf Template**:
   - Replaced static content with Thymeleaf placeholders.
   - Integrated with Spring Boot to dynamically populate resume data from the database.
3. **API Development**:
   - Created RESTful API endpoints using Spring Boot for managing a single comprehensive Resume object.
   - Education, Experience, and Skills are modeled as collections embedded within the Resume entity using JPA `@Embeddable` and `@ElementCollection`.
   - Implemented CRUD operations for the Resume via `ResumeService` and `ResumeRepository`. Utilizes Spring Data JPA and Lombok for reducing boilerplate code.
4. **Database Setup**:
   - Configured PostgreSQL for data storage.
   - Dockerized PostgreSQL for easy management and consistency across environments.
5. **Interactive Documentation**: 
   - Integrated Swagger UI for interactive API documentation.
6. **Error Handling and Validation**: 
   - Implemented global exception handling with `@ControllerAdvice`.
   - Added data validation.
7. **Security Authentication**: 
   - Basic authentication for the API.
   - Configured security to allow public access to GET endpoints and restrict POST, PUT, and DELETE endpoints to authenticated users.
   - Utilized Spring Security and implemented `MyUserDetailsService` for user authentication.
   - Secured password storage with a password encoder.
8. **Continuous integration and Continuous Delivery CI/CD**
   - CI/DI deployment with GitHub and render.

---

## Technologies Used.

- **Spring Boot**: For building API endpoints and handling requests/responses.
- **PostgreSQL**: Relational database for managing resume data.
- **Lombok**: For reducing boilerplate code with annotations.
- **Thymeleaf**: Template engine for rendering web pages.
- **Swagger UI**: For interactive API documentation.
- **Docker**: For containerizing PostgreSQL.
- **Spring Security**: For securing API endpoints and managing user authentication.
- **Continuous integration and continuous delivery CI/CD**

---

## Features

- **CRUD Operations**: Manage a comprehensive Resume object including embedded lists for educational experiences, work experiences, and skills.
- **Interactive Documentation**: Explore and test API endpoints with Swagger UI.
- **Modular Structure**: Clean and scalable project structure.
- **Security Authentication**: Secure API endpoints with basic authentication.

---

## Installation

To run the Personal Resume Website API locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/AngelosGi/personal-website.git
   # Or use SSH
   git clone git@github.com:AngelosGi/personal-website.git
   ```

2. Install dependencies:
   ```bash
   mvn install
   ```

3. **Database Setup**:
   - Ensure a PostgreSQL database is set up and running.
   - Update the database connection details in `application.properties`.
   - Run the application to create the initial database schema.
   - **Note:** To use authenticated endpoints (POST/PUT/DELETE), you must first create a user in the `app_user` table with a BCrypt-hashed password (see `PasswordEncoderGenerator.java` for hashing).

4. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   # Or just press the play button in your IDE
   ```

5. Access the API at `http://localhost:8080/api/...` in your browser or API client.
6. Access the resume template at `http://localhost:8080/resume/{id}`.

## Usage

- Access the Swagger documentation at `http://localhost:8080/swagger-ui/index.html` to explore and interact with the API endpoints.
- Use tools like Postman to make requests to the API endpoints.
- View the rendered HTML resume at `http://localhost:8080/resume/{id}` (replace `{id}` with a valid resume ID).

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

- ~~Auth~~ Done
- ~~hide db credentials for production. 👀~~ Done (add env variables)
- ~~add skills from db~~ Done
- Dockerize app & deploy (or something)
- Add more resume templates

---
