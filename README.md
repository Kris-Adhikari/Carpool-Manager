# RideBuddy

A backend for a carpool management system designed to reduce students’ commute times.

- **Backend:** Built a Java Spring Boot backend, incorporating REST APIs and OpenID Connect with Keycloak for authentication
- **Database:** Stored ride data in a PostgreSQL database, utilizing Docker to containerize the database for consistent development environments.

---

## Prerequisites

Before running this project, ensure you have the following installed:
- **Java Development Kit (JDK)**: Version 8 or higher
- **PostgreSQL Database**
- **Docker**: Optional, for database containerization

---

## Installation and Setup

### Backend:
1. Clone repo and open up backend
2. Update application.properties in src/resources with PostgreSQL creds
3. Run application

# Keycloak

Add to `src/main/resources/application.properties`:

`spring.security.oauth2.resourceserver.jwt.issuer-uri=https://<keycloak-host>/realms/<realm-name>`

Create a client in that realm, then call protected endpoints with the Bearer token Keycloak issues
