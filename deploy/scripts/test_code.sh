#!/bin/bash
set -e

echo "Running Gradle build and tests..."

# Run Gradle build and tests from the project root
./gradlew clean build test

echo "Gradle build and tests completed!"