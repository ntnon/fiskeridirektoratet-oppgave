# Fiskeregister - Docker Deployment

A containerized Spring Boot backend and React frontend application for managing fish registry data.

## Prerequisites

- Docker (version 20.10 or higher)
- Docker Compose (version 2.0 or higher)

## Quick Start

Deploy the entire application with a single command:

```bash
docker-compose up -d --build
```

The application will be available at:
- **Frontend**: http://localhost
- **Backend API**: http://localhost:8080

## Docker Commands

### Start the application
```bash
# Build and start in detached mode
docker-compose up -d --build

# Start without rebuilding (if images already exist)
docker-compose up -d
```

### Stop the application
```bash
docker-compose down
```

### View logs
```bash
# All services
docker-compose logs -f

# Backend only
docker-compose logs -f backend

# Frontend only
docker-compose logs -f frontend
```

### Rebuild images
```bash
docker-compose build

# Rebuild a specific service
docker-compose build backend
docker-compose build frontend
```

### Check service status
```bash
docker-compose ps
```

### Clean up everything
```bash
# Stop and remove containers, networks, volumes, and images
docker-compose down -v --rmi all
```

## Architecture

The application consists of:

- **Backend**: Spring Boot application with H2 in-memory database
  - Runs on port 8080
  - REST API endpoints at `/api/fish`
  
- **Frontend**: React application served by Nginx
  - Runs on port 80
  - Proxies API requests to backend via Nginx

## Development

For local development without Docker:

### Backend
```bash
cd backend
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

The frontend development server includes a proxy configuration to forward API requests to `http://localhost:8080`.

## Troubleshooting

### Port conflicts
If ports 80 or 8080 are already in use, modify the port mappings in `docker-compose.yml`:

```yaml
services:
  backend:
    ports:
      - "8081:8080"  # Change 8081 to your preferred port
  frontend:
    ports:
      - "3000:80"    # Change 3000 to your preferred port
```

### Container startup issues
Check the logs for specific error messages:
```bash
docker-compose logs backend
docker-compose logs frontend
```

### Force rebuild after code changes
```bash
docker-compose up -d --build --force-recreate
```
