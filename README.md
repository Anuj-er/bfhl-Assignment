# BFHL API Assignment

Spring Boot 3 / Java 17 solution for the Bajaj Finserv API round.

## What it provides

`POST /bfhl` accepts a JSON object with a `data` array and returns:

- success status, user details, and roll number
- odd/even numbers as the original strings
- uppercase alphabet-only values
- special-character values
- an arbitrary-precision numeric sum, returned as a string
- every alphabetic character reversed and transformed to alternating upper/lower case

The project includes request/response DTOs, a service interface and implementation, validation, centralised error handling, unit tests, and endpoint integration tests.

## Set your submission identity

Before deployment, set these environment variables in Railway, Render, or your chosen host. They are deliberately not hard-coded into the repository.

| Variable | Example | Purpose |
| --- | --- | --- |
| `FULL_NAME` | `Anuj Kumar` | Becomes lowercase underscores in `user_id` |
| `DOB_DDMMYYYY` | `01042005` | Date portion of `user_id` |
| `EMAIL` | `anuj1699.be23@chitkara.edu.in` | Response `email` |
| `ROLL_NUMBER` | `2310991699` | Response `roll_number` |

For the example above, `user_id` is `anuj_kumar_01042005`.

## Run and test locally

```bash
mvn spring-boot:run
mvn test
curl --request POST http://localhost:8080/bfhl \
  --header 'Content-Type: application/json' \
  --data '{"data":["a","1","334","4","R","$"]}'
```

Expected fields include `odd_numbers: ["1"]`, `even_numbers: ["334", "4"]`, `sum: "339"`, and `concat_string: "Ra"`.

## Deploy to Railway

1. Create a new GitHub repository and push this project.
2. In Railway, choose **New Project** -> **Deploy from GitHub repo** and select it. Railway detects the included `Dockerfile`.
3. Add the four identity variables above in the Railway **Variables** panel.
4. Deploy, then open `https://<your-domain>/bfhl` in the endpoint you submit (the API only accepts `POST`).
5. Test the final URL with the `curl` command above, replacing `localhost:8080` with your Railway domain.

Do not submit the base domain alone: submit the complete URL ending in `/bfhl`.
