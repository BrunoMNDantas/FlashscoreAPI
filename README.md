# Flashscore API

## Index

- [Overview](#overview)
- [Supported Sports](#supported-sports)
- [Future Goals](#future-goals)
- [Tech Stack](#tech-stack)
- [UML Diagrams](#uml-diagrams)
- [Deployment](#deployment)
  - [Deployment Workflow (`deploy.yml`)](#deployment-workflow-deployyml)
  - [Testing Workflow (`test.yml`)](#testing-workflow-testyml)
  - [GitHub Secrets](#github-secrets)
- [Setup & Installation](#setup--installation)
  - [Prerequisites](#prerequisites)
  - [Installation Steps](#installation-steps)
  - [API Documentation](#api-documentation)
- [License](#license)
- [Contributors](#contributors)

## Overview

Flashscore API is a Java-based web scraping application that extracts sports data from [Flashscore.com](https://www.flashscore.com).

The API is implemented using **Spring Boot**, **Selenium**, and **Swagger** for documentation. The project is deployed using **Railway** and includes CI/CD automation with **GitHub Actions**.

You can explore the API [here](https://flashscoreapi-production.up.railway.app/swagger-ui/index.html#/).

## Supported Sports

Currently, the Flashscore API supports scraping data for the following sports:

- ⚽ Football (Soccer)

## Future Goals

- Expand the number of supported sports:
  - 🏀 Basketball
  - ⚾ Baseball
  - 🏈 American Football
  - 🏒 Ice Hockey
- Improve scraping efficiency and data accuracy.
- Implement caching mechanisms for frequently accessed data.
- Add authentication and rate limiting.

## Tech Stack

- **Backend:** Java, Spring Boot, Swagger
- **Containerization:** Docker
- **Web Scraping:** Selenium
- **CI/CD:** GitHub Actions, Railway, Telegram
- **Testing:** JUnit, Postman

## UML Diagrams

Below are UML diagrams representing the domain entities of the application:

### Entity Relationship Diagram

#### Sport
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Sport.png" >
</p>

#### Region
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Region.png" >
</p>

#### Competition
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Competition.png" >
</p>

#### Season
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Season.png" >
</p>

#### Match
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Match.png" >
</p>

#### Event
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Event.png" >
</p>

#### Team
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Team.png" >
</p>

#### Player
<p align="center">
	<img src = "https://raw.githubusercontent.com/BrunoMNDantas/FlashscoreAPI/master/docs/Player.png" >
</p>

## Deployment

The project uses **GitHub Actions** and **Railway** for automated deployment and testing:

### Deployment Workflow (`deploy.yml`)

- Pushing to `dev` branch triggers deployment to the **development** environment on Railway.
- JUnit tests and Postman tests run after the development deployment.
- If all tests pass, the code is automatically merged into `main`.
- Pushing to `main` triggers deployment to the **production** environment on Railway.
- A job inside this workflow notifies Telegram of the start and finish of the deployment process.

### Testing Workflow (`test.yml`)

- A scheduled workflow runs daily to test the **production** environment.
- This ensures the API remains operational and detects potential issues early.
- A job inside this workflow notifies Telegram of the start and finish of the testing process.

### GitHub Secrets

Both workflows use the following GitHub Secrets for authentication and configuration:

- **Telegram Secrets:**
  - `TELEGRAM_BOT_TOKEN` - The API token used to send messages via Telegram bot.
  - `TELEGRAM_DEPLOYMENT_CHAT_ID` - The chat ID for deployment notifications.
  - `TELEGRAM_PRODUCTION_CHAT_ID` - The chat ID for production notifications.

- **Railway Secrets:**
  - `RAILWAY_API_KEY` - API key used to authenticate and deploy the application on Railway.
  - `RAILWAY_PROJECT_ID` - The unique identifier of the Railway project.
  - `RAILWAY_SERVICE_ID` - The identifier of the specific Railway service being deployed.
  - `RAILWAY_ENVIRONMENT_ID` - The identifier for the environment where the service is deployed.

- **Postman & API Secrets:**
  - `POSTMAN_API_KEY` - API key used to authenticate Postman requests.
  - `PRODUCTION_SERVER_URL` - The base URL of the production server.
  - `PRODUCTION_SCHEMA_URL` - The schema URL of the production server.
  - `DEVELOPMENT_SERVER_URL` - The base URL of the development server.
  - `DEVELOPMENT_SCHEMA_URL` - The schema URL of the development server.

- **AWS S3 Secrets:**
  - `S3_BUCKET` - The S3 bucket name used for storage.
  - `S3_REGION` - The AWS region where the S3 bucket is hosted.
  - `S3_ACCESS_KEY` - The AWS access key ID for authentication.
  - `S3_SECRET_KEY` - The AWS secret access key for authentication.

These secrets are stored in GitHub and used within workflows to manage deployments and notifications.

You can access the API documentation for the production environment [here](https://flashscoreapi-production.up.railway.app/swagger-ui/index.html#/), and for the development environment [here](https://flashscoreapi-development.up.railway.app/swagger-ui/index.html#/).

## Setup & Installation

### Prerequisites

Ensure you have the following installed:

- Java 17+
- Gradle
- Docker (optional, for containerized deployment)
- A system environment variable `DRIVER_PATH` must be declared containing the path to the Chrome driver.
- AWS S3 credentials must be set as system environment variables (`S3_BUCKET`, `S3_REGION`, `S3_ACCESS_KEY`, `S3_SECRET_KEY`).

### Installation Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/BrunoMNDantas/FlashscoreAPI.git
   cd FlashscoreAPI
   ```
2. Build the project:
   ```sh
   ./gradlew clean build -x test
   ```
3. Run the application locally:
   ```sh
   ./gradlew bootRun
   ```

### API Documentation

Once the application is running, you can access the API documentation at:

```
http://localhost:8080/v3/api-docs
```

You can also view the Swagger UI interface at:

```
http://localhost:8080/swagger-ui/index.html
```

## License

This project is licensed under the GNU License - see the [LICENSE](LICENSE) file for details.

## Contributors

- [BrunoMNDantas](https://github.com/BrunoMNDantas)

