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
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080

## Docker Commands

```bash
# Build and start in detached mode
docker-compose up -d --build

# Start without rebuilding (if images already exist)
docker-compose up -d

# Stop the application
docker-compose down

# View logs
docker-compose logs -f
docker-compose logs -f backend
docker-compose logs -f frontend

# Rebuild images
docker-compose build
docker-compose build backend
docker-compose build frontend

# Check service status
docker-compose ps

# Stop and remove containers, networks, volumes, and images
docker-compose down -v --rmi all
```

# Architecture

The application is split into two main layers:

## Backend
- **Framework & Language**: Spring Boot, Java 17
- **Database**: H2 in-memory database
- **Port**: 8080
- **Endpoints**: REST API under `/api/fish`
- **Request Flow**:
  `Controller → Service → Repository (Spring Data JPA) → Hibernate (JPA Provider) → JDBC → H2 Database`
- **Responsibilities**:
  - Handles CRUD operations for fish data
  - Validates and processes incoming requests
  - Persists data to the in-memory database
- **Development**:
  - **Build Tool**: Maven
  - **Testing**: JUnit, Mockito

## Frontend
- **Framework & Language**: React, JavaScript
- **Server**: Served via Nginx
- **Port**: 3000 on host (container exposes port 80)
- **Responsibilities**:
  - Provides a user interface to view, create, update, and delete fish
  - Consumes backend API endpoints
  - Implements features like sorting, editing, and confirmation modals
  - **API Proxying**: Nginx proxies frontend API calls to the backend at `/api/fish`
- **Development**:
  - **Build Tool**: npm
  - **Styling**: Tailwindcss



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



# Fish API Overview
**Base URL:** `/api/fish`

**Content Type:** JSON

**Validation:** Jakarta Bean Validation (`@Valid`)

## **Endpoints**

| Method | Path | Request Body / Params | Description | Response |
|--------|------|---------------------|-------------|----------|
| POST   | `/` | JSON body: `name` (String), `species` (String), `lengthCm` (Integer), `weightKg` (Double) | Create a new fish | `201 Created` with created Fish |
| GET    | `/` | None | Retrieve all fish | `200 OK` with list of Fish |
| GET    | `/{id}` | Path param: `id` (Long) | Retrieve a fish by ID | `200 OK` with Fish or `404 Not Found` |
| PUT    | `/{id}` | Path param: `id` (Long), JSON body same as POST | Update a fish by ID | `200 OK` with updated Fish or `404 Not Found` |
| DELETE | `/{id}` | Path param: `id` (Long) | Delete a fish by ID | `204 No Content` or `404 Not Found` |
| GET    | `/species` | None | Retrieve all distinct fish species | `200 OK` with list of species strings |
| GET    | `/species/{species}` | Path param: `species` (String) | Retrieve all fish of a given species | `200 OK` with list of Fish |


# Troubleshooting

## Port conflicts
If ports 3000 or 8080 are already in use, modify the port mappings in `docker-compose.yml`:

```yaml
services:
  backend:
    ports:
      - "8081:8080"  # Change 8081 to your preferred port
  frontend:
    ports:
      - "3001:80"    # Change 3001 to your preferred port
```

## Container startup issues
Check the logs for specific error messages:
```bash
docker-compose logs backend
docker-compose logs frontend
```

## Force rebuild after code changes
```bash
docker-compose up -d --build --force-recreate
```
