# serverless

Container orchestration and API deployment across AWS (ECS/Fargate), GCP (Cloud Run/GKE) and Azure (Container Apps/AKS).

The repo contains a small Flask app that checks connectivity to a PostgreSQL database and shows the server hostname and IPv4 address. It is packaged as a Docker image and built and deployed through Jenkins.

## Project structure

```
.
├── Dockerfile          # Builds the app image (python:3)
├── Jenkinsfile         # CI/CD pipeline
├── LICENSE
├── data/
│   ├── main.py         # Flask application
│   ├── requirements.txt
│   └── templates/
│       └── index.html  # Page template
└── vars/               # Jenkins shared library steps
    ├── awsLogin.groovy
    └── infraRegion.groovy
```

## How it works

Opening `/` does two things:

1. Connects to PostgreSQL and runs `SELECT version();`
2. Reads the container's hostname and IPv4 address

The page then shows **Database connected** or **Database not connected**, followed by the server name and IPv4 address. This is handy for confirming which container or instance answered a request behind a load balancer.

## Configuration

The app is configured with environment variables:

| Variable    | Description                 |
|-------------|-----------------------------|
| `DB_HOST`   | PostgreSQL host             |
| `DB_PORT`   | PostgreSQL port             |
| `DB_NAME`   | Database name               |
| `DB_USER`   | Database user               |
| `DB_PASSWD` | Database password           |
| `APP_PORT`  | Application port (see note) |

> **Note:** `main.py` currently starts Flask on port `5000` and does not use `APP_PORT` when calling `app.run()`. Map port 5000 when running the container, or update the code to use `APP_PORT`.

## Run locally

```bash
cd data
pip install -r requirements.txt

export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=mydb
export DB_USER=myuser
export DB_PASSWD=mypassword

python main.py
```

Then open <http://localhost:5000>.

## Run with Docker

```bash
docker build -t serverless .

docker run -p 5000:5000 \
  -e DB_HOST=<db-host> \
  -e DB_PORT=5432 \
  -e DB_NAME=<db-name> \
  -e DB_USER=<db-user> \
  -e DB_PASSWD=<db-password> \
  serverless
```

## Deployment targets

| Cloud | Services            |
|-------|---------------------|
| AWS   | ECS, Fargate        |
| GCP   | Cloud Run, GKE      |
| Azure | Container Apps, AKS |

## License

See [LICENSE](LICENSE).
