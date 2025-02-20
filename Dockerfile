# --- STAGE 1: Build the Application ---
FROM eclipse-temurin:17-jdk AS builder

# Set working directory for the build process
WORKDIR /app

# Copy Gradle Wrapper and project files
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Give execute permissions to Gradle Wrapper
RUN chmod +x gradlew

# Build the application inside the container
RUN ./gradlew clean build -x test

# --- STAGE 2: Create a Lightweight Final Runtime Container ---
FROM eclipse-temurin:17-jre

# Set working directory for the runtime
WORKDIR /app

# Install necessary dependencies in the builder stage
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    curl \
    unzip \
    gnupg \
    jq

# Copy the Selenium setup script
COPY deploy/scripts/build_selenium_environment.sh /app/build_selenium_environment.sh

# Grant execution permissions
RUN chmod +x /app/build_selenium_environment.sh

# Run the Selenium environment setup script
RUN /bin/bash /app/build_selenium_environment.sh

ENV CHROME_BIN=/usr/bin/google-chrome \
    CHROMEDRIVER_BIN=/usr/bin/chromedriver \
    DRIVER_PATH=/usr/bin/chromedriver

# Copy the generated JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]