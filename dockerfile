# Use Eclipse Temurin JDK 17 as the base image
FROM eclipse-temurin:17-jdk

# Set environment variables for Chrome and ChromeDriver
ENV CHROME_BIN=/usr/bin/google-chrome \
    CHROMEDRIVER_BIN=/usr/bin/chromedriver

# Install necessary dependencies, including jq
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    gnupg \
    jq \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

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
    rm -rf /tmp/chromedriver.zip /tmp/chromedriver-linux64 && \
    chmod +x /usr/bin/chromedriver

# Create a directory for the application
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY build/libs/flashscore.api-1.0.0.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]