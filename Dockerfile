# First stage: Build the application
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Copy the project files into the container
COPY . /app

# Package the application using Maven
# Use a fixed artifact name during build for easier copying later
RUN mvn -f /app/pom.xml clean package -DskipTests -DfinalName=app

# Second stage: Run the application
FROM eclipse-temurin:21-jdk

# Define argument for the JAR file path
ARG JAR_FILE=/app/app.jar

# Copy the packaged application from the build stage using the fixed name
COPY --from=build /app/target/app.jar ${JAR_FILE}

# Expose the port the application runs on
EXPOSE 8080

# Run the Spring Boot application using the argument
ENTRYPOINT ["java","-jar", "${JAR_FILE}"]