# First stage: Build the application
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the project files
COPY src ./src

# Package the application using Maven (without -DfinalName)
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Define a fixed path for the app JAR in the final image
ARG JAR_DEST_PATH=app.jar

# Copy the JAR using a wildcard from the build stage's target directory
# Assumes only one JAR is built by the package phase
COPY --from=build /app/target/*.jar ${JAR_DEST_PATH}

# Expose the port the application runs on
EXPOSE 8080

# Run the Spring Boot application using the fixed destination path
ENTRYPOINT ["java","-jar", "/app/app.jar"]