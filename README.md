# Personal Resume Website API


This project is a Spring Boot-based APP for managing a personal Resume website. It was inspired by the static HTML and CSS resume found in the [Thomashighbaugh's repository](https://github.com/Thomashighbaugh/resume).

Here are the steps taken to create this project:

1. **Inspiration**: The initial resume template was taken from the above repository. It provided a static HTML and CSS structure for a resume.

2. **Conversion to Thymeleaf Template**: The static HTML and CSS were converted into a Thymeleaf template. This involved:
   - Replacing static content with Thymeleaf placeholders.
   - Integrating the template with the Spring Boot application to dynamically populate resume data from the database.

3. **API Development**: 
   - Created endpoints for managing resume details, education, work experiences, etc.
   - Implemented CRUD operations to interact with the PostgreSQL database.
   - Utilized Spring Data JPA for database interactions and Lombok for reducing boilerplate code.

4. **Database Setup**: 
   - Configured PostgreSQL as the database for storing resume-related data.
   - Dockerized the PostgreSQL setup for easy management and consistency across environments.

5. **Interactive Documentation**: 
   - Integrated Swagger UI to provide interactive API documentation.
   - Enabled users to explore and test the API endpoints visually.

6. **Error Handling and Validation**: 
   - Implemented a global exception handler using `@ControllerAdvice`.
   - Added validation to ensure data integrity and correctness.

---

## Technologies Used

- **Spring Boot:** Spring Boot is used as the primary web framework for building the API endpoints, providing high performance and easy-to-use features for request handling and response generation.
- **PostgreSQL:** PostgreSQL serves as the relational database management system for storing and managing the data of educational and work experiences. It provides robust features for data integrity and scalability.
- **Lombok:** Lombok is leveraged for reducing boilerplate code with annotations, ensuring that incoming requests and outgoing responses adhere to defined schemas. It helps maintain consistency and correctness in data handling.
- **Thymeleaf:** Thymeleaf is utilized as the template engine for rendering web pages, providing fast and efficient handling of HTML templates.
- **Swagger UI:** Swagger UI is integrated to provide interactive documentation for the API endpoints, allowing users to explore and test the available functionalities visually.
- **Docker:** Docker is employed to containerize the PostgreSQL database, allowing for easy setup and management of the database environment. It ensures consistency and portability across different development and deployment environments.

---

## Features

- **CRUD Operations:** Create, read, update, and delete educational experiences, work experiences, and resume details.
- **Interactive Documentation:** Swagger UI provides an interactive API documentation interface for easy exploration and testing.
- **Modular Structure:** The project follows a clean and modular structure for scalability and maintainability.

---

## Installation

To run the Personal Website API locally, follow these steps:

1. Clone the repository:

   ```bash
   Https: git clone https://github.com/AngelosGi/personal-website.git
   SSH: git clone git@github.com:AngelosGi/personal-website.git
   ```

2. Install dependencies:

   ```bash
   mvn install
   ```

3. **Database Setup:**

   - **Database Connection:** Ensure that you have a PostgreSQL database set up and running. Update the database connection details in the `application.properties` file to match your database configuration.
   - **Fresh Setup:** Run the application to create the initial database schema.

4. Start the Spring Boot application:

5. Access the API at `http://localhost:8080` in your browser or API client.

6. Access the resume template at `http://localhost:8080/resume`

---

## Usage

- Access the Swagger documentation at `http://localhost:8080/swagger-ui.html` to explore and interact with the API endpoints.
- Use tools like Postman to make requests to the API endpoints.

---

## API Endpoints

### Education

- **GET /api/education**: Retrieve all education records.
- **GET /api/education/{id}**: Retrieve a specific education record by ID.
- **POST /api/education**: Create a new education record.
- **PUT /api/education/{id}**: Update an existing education record by ID.
- **DELETE /api/education/{id}**: Delete an education record by ID.

### Experience

- **GET /api/experience**: Retrieve all experience records.
- **GET /api/experience/{id}**: Retrieve a specific experience record by ID.
- **POST /api/experience**: Create a new experience record.
- **PUT /api/experience/{id}**: Update an existing experience record by ID.
- **DELETE /api/experience/{id}**: Delete an experience record by ID.

### Resume

- **GET /resume**: Retrieve the resume for rendering with Thymeleaf.
- **GET /api/resume/{id}**: Retrieve a specific resume record by ID.
- **GET /api/resume**: Retrieve all resume records.
- **POST /api/resume**: Create a new resume record.
- **PUT /api/resume/{id}**: Update an existing resume record by ID.
- **DELETE /api/resume/{id}**: Delete a resume record by ID.

---
By following these steps and utilizing the provided endpoints, you can effectively manage and interact with the personal website data through this API.


```
ToDo - auth
Bug - when updating resume info via api, education and experience_responsibilities lose the ""resume_id"" that links them to the resume**.
```
