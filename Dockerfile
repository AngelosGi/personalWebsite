# First stage: Build the application
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Copy the project files into the container
COPY . /app

# Package the application using Maven
RUN mvn -f /app/pom.xml clean package -DskipTests

# Second stage: Run the application
FROM eclipse-temurin:21-jdk

# Copy the packaged application from the build stage
COPY --from=build /app/target/personal-website-0.0.1-SNAPSHOT.jar /app/personal-website-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/app/personal-website-0.0.1-SNAPSHOT.jar"]