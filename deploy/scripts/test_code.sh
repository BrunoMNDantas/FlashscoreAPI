#!/bin/bash
set -e

echo "Running Gradle build and tests..."

# Run Gradle build and tests from the project root
./gradlew clean build test --tests "com.github.brunomndantas.flashscore.api.transversal.driverSupplier.*"

echo "Gradle build and tests completed!"