# --- STAGE 1: Build the Application ---
FROM eclipse-temurin:17-jdk AS builder

# Set environment variables for Chrome and ChromeDriver
ENV CHROME_BIN=/usr/bin/google-chrome \
    CHROMEDRIVER_BIN=/usr/bin/chromedriver \
    DRIVER_PATH=/usr/bin/chromedriver

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

# Install Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends google-chrome-stable

# Get the exact ChromeDriver version matching installed Chrome
RUN CHROME_VERSION=$(google-chrome --version | grep -oP '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+') && \
    echo "Detected Chrome Version: $CHROME_VERSION" && \
    CHROMEDRIVER_VERSION=$(curl -sS "https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json" | \
    jq -r --arg chrome_version "$CHROME_VERSION" '.versions[] | select(.version==$chrome_version) | .version') && \
    echo "Installing ChromeDriver Version: $CHROMEDRIVER_VERSION" && \
    CHROMEDRIVER_URL=$(curl -sS "https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json" | \
    jq -r --arg chromedriver_version "$CHROMEDRIVER_VERSION" '.versions[] | select(.version==$chromedriver_version) | .downloads.chromedriver[] | select(.platform=="linux64") | .url') && \
    wget -q "$CHROMEDRIVER_URL" -O /tmp/chromedriver.zip && \
    unzip /tmp/chromedriver.zip -d /tmp/ && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/bin/chromedriver && \
    chmod +x /usr/bin/chromedriver

ENV CHROME_BIN=/usr/bin/google-chrome \
    CHROMEDRIVER_BIN=/usr/bin/chromedriver \
    DRIVER_PATH=/usr/bin/chromedriver

# Copy the generated JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]