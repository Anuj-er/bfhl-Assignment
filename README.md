# BFHL API Assignment

> A production-ready Spring Boot REST API for the Bajaj Finserv Health API round.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Deploy-Docker-2496ED.svg)](https://www.docker.com/)

## Overview

This project exposes a single REST endpoint:

```http
POST /bfhl
```

Live deployment:

```text
https://bfhl-assignment-odtq.onrender.com/bfhl
```

It accepts an array of values and returns categorized numbers, alphabets, special characters, the numeric sum, and the required alternating-case reversed alphabet string.

The implementation is intentionally clean and assignment-friendly:

- Spring Boot 3 + Java 17
- Request and response DTOs
- Service interface and implementation
- Validation and centralized error handling
- Unit tests and controller tests
- Dockerfile for Render/Railway deployment
- Numbers returned as strings, as required
- Arbitrary-size integer support using `BigInteger`

## Submission Identity

The API is configured with the required user details:

| Field | Value |
| --- | --- |
| Full name | `Anuj Kumar` |
| Date of birth | `01042005` |
| Email | `anuj1699.be23@chitkara.edu.in` |
| Roll number | `2310991699` |
| Generated `user_id` | `anuj_kumar_01042005` |

These values are also configurable through environment variables:

| Environment variable | Value |
| --- | --- |
| `FULL_NAME` | `Anuj Kumar` |
| `DOB_DDMMYYYY` | `01042005` |
| `EMAIL` | `anuj1699.be23@chitkara.edu.in` |
| `ROLL_NUMBER` | `2310991699` |

## API Contract

### Request

```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

### Success Response

```json
{
  "is_success": true,
  "user_id": "anuj_kumar_01042005",
  "email": "anuj1699.be23@chitkara.edu.in",
  "roll_number": "2310991699",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

### Rules Implemented

| Output field | Logic |
| --- | --- |
| `is_success` | `true` for successful processing |
| `user_id` | Lowercase full name with underscores + DOB in `ddMMyyyy` format |
| `odd_numbers` | Numeric strings representing odd numbers |
| `even_numbers` | Numeric strings representing even numbers |
| `alphabets` | Alphabet-only values converted to uppercase |
| `special_characters` | Values that are neither pure numbers nor pure alphabets |
| `sum` | Sum of all numeric values, returned as a string |
| `concat_string` | All alphabetic characters reversed, alternating caps from uppercase |

## More Examples

### Example 1

```bash
curl --request POST http://localhost:8080/bfhl \
  --header "Content-Type: application/json" \
  --data '{"data":["2","a","y","4","&","-","*","5","92","b"]}'
```

Expected key fields:

```json
{
  "odd_numbers": ["5"],
  "even_numbers": ["2", "4", "92"],
  "alphabets": ["A", "Y", "B"],
  "special_characters": ["&", "-", "*"],
  "sum": "103",
  "concat_string": "ByA"
}
```

### Example 2

```bash
curl --request POST http://localhost:8080/bfhl \
  --header "Content-Type: application/json" \
  --data '{"data":["A","ABCD","DOE"]}'
```

Expected key fields:

```json
{
  "alphabets": ["A", "ABCD", "DOE"],
  "sum": "0",
  "concat_string": "EoDdCbAa"
}
```

## Run Locally

### Prerequisites

- Java 17
- Maven 3.9+

### Start the API

```bash
mvn spring-boot:run
```

The app starts on:

```text
http://localhost:8080
```

### Test the endpoint

```bash
curl --request POST http://localhost:8080/bfhl \
  --header "Content-Type: application/json" \
  --data '{"data":["a","1","334","4","R","$"]}'
```

### Run tests

```bash
mvn test
```

### Build the JAR

```bash
mvn package
```

The generated JAR will be available at:

```text
target/bfhl-api-1.0.0.jar
```

## Deploy on Render

This repository includes a root-level `Dockerfile`, so the simplest Render setup is a Docker Web Service.

### Step-by-step Render deployment

1. Push this repository to GitHub.

2. Open the Render Dashboard:

   ```text
   https://dashboard.render.com
   ```

3. Click **New +** -> **Web Service**.

4. Connect your GitHub account and select this repository:

   ```text
   Anuj-er/bfhl-Assignment
   ```

5. Configure the service:

   | Render field | Value |
   | --- | --- |
   | Name | `bfhl-assignment` |
   | Language | `Docker` |
   | Branch | `main` |
   | Root Directory | Leave empty |
   | Dockerfile Path | `Dockerfile` |
   | Instance Type | Free/Starter is enough |

6. Add environment variables from the **Environment** section:

   | Key | Value |
   | --- | --- |
   | `FULL_NAME` | `Anuj Kumar` |
   | `DOB_DDMMYYYY` | `01042005` |
   | `EMAIL` | `anuj1699.be23@chitkara.edu.in` |
   | `ROLL_NUMBER` | `2310991699` |

7. Click **Deploy Web Service**.

8. After deployment succeeds, Render gives you a public URL:

   ```text
   https://bfhl-assignment-odtq.onrender.com
   ```

9. Your final API endpoint will be:

   ```text
   https://bfhl-assignment-odtq.onrender.com/bfhl
   ```

10. Test the deployed endpoint:

    ```bash
    curl --request POST https://bfhl-assignment-odtq.onrender.com/bfhl \
      --header "Content-Type: application/json" \
      --data '{"data":["a","1","334","4","R","$"]}'
    ```

Submit the complete deployed endpoint ending in `/bfhl`, not just the base Render URL.

## Docker

Build and run locally with Docker:

```bash
docker build -t bfhl-api .
docker run --rm -p 8080:8080 bfhl-api
```

Then test:

```bash
curl --request POST http://localhost:8080/bfhl \
  --header "Content-Type: application/json" \
  --data '{"data":["a","1","334","4","R","$"]}'
```

## Project Structure

```text
.
├── Dockerfile
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/com/bajajfinserv/bfhl
    │   │   ├── config
    │   │   ├── controller
    │   │   ├── dto
    │   │   ├── exception
    │   │   └── service
    │   └── resources/application.yml
    └── test/java/com/bajajfinserv/bfhl
```

## Validation and Errors

If the request body is invalid, the API returns a graceful error response:

```json
{
  "is_success": false,
  "user_id": "anuj_kumar_01042005",
  "email": "anuj1699.be23@chitkara.edu.in",
  "roll_number": "2310991699",
  "odd_numbers": [],
  "even_numbers": [],
  "alphabets": [],
  "special_characters": [],
  "sum": "0",
  "concat_string": "",
  "error": "data: data is required"
}
```

## Troubleshooting Render

| Problem | Fix |
| --- | --- |
| Service deploys but endpoint fails | Make sure you are sending a `POST` request to `/bfhl` |
| `GET /bfhl` does not work | Expected behavior: the assignment requires `POST /bfhl` |
| Wrong identity in response | Check the four environment variables in Render |
| Build fails | Confirm Render is using `Language = Docker` and the root `Dockerfile` |
| First request is slow | Free Render services can take time to wake up after inactivity |

## Final Submission URL Format

Use this format:

```text
https://<your-render-service-name>.onrender.com/bfhl
```

Example:

```text
https://bfhl-assignment-odtq.onrender.com/bfhl
```

---

Built for the Bajaj Finserv Health API round.
