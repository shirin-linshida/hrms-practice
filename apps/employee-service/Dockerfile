# Stage 1: Build the application
FROM openjdk:17-jdk-alpine as build

# Set the working directory
WORKDIR /app/employee-service

# Copy the Gradle wrapper files from the correct location
COPY ./apps/employee-service/gradlew ./gradlew
COPY ./apps/employee-service/gradle ./gradle

# Copy the source code to the build container
COPY ./apps/employee-service/ ./

# Ensure the Gradle wrapper has execution permission
RUN chmod +x ./gradlew

# Run the Gradle build to create the JAR file
RUN ./gradlew clean build

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/employee-service/build/libs/hrms-0.0.1-SNAPSHOT.jar employee-service.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/employee-service.jar"]
