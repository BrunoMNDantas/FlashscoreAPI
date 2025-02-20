#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status

# Update and install dependencies
echo "Updating package lists and installing dependencies..."
apt-get update && apt-get install -y --no-install-recommends \
    wget \
    curl \
    unzip \
    gnupg \
    jq

# Install Google Chrome
echo "Installing Google Chrome..."
wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add -
echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list
apt-get update && apt-get install -y --no-install-recommends google-chrome-stable

# Get the exact ChromeDriver version matching installed Chrome
CHROME_VERSION=$(google-chrome --version | grep -oP '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+')
echo "Detected Chrome Version: $CHROME_VERSION"

CHROMEDRIVER_VERSION=$(curl -sS "https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json" | \
    jq -r --arg chrome_version "$CHROME_VERSION" '.versions[] | select(.version==$chrome_version) | .version')
echo "Installing ChromeDriver Version: $CHROMEDRIVER_VERSION"

CHROMEDRIVER_URL=$(curl -sS "https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json" | \
    jq -r --arg chromedriver_version "$CHROMEDRIVER_VERSION" '.versions[] | select(.version==$chromedriver_version) | .downloads.chromedriver[] | select(.platform=="linux64") | .url')

wget -q "$CHROMEDRIVER_URL" -O /tmp/chromedriver.zip
unzip /tmp/chromedriver.zip -d /tmp/
mv /tmp/chromedriver-linux64/chromedriver /usr/bin/chromedriver
chmod +x /usr/bin/chromedriver

echo "Selenium environment successfully built!"
