# Stage 1: Build the application
FROM openjdk:17-jdk-alpine AS build

WORKDIR /app/notification-service

# Copy the Gradle wrapper files and source code
COPY ./apps/notification-service/gradlew ./gradlew
COPY ./apps/notification-service/gradle ./gradle
COPY ./apps/notification-service/ ./

# Make the Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew clean build

# Debugging step to list files
RUN ls -al /app/notification-service/build/libs/

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/notification-service/build/libs/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/notification-service.jar"]
