# CityApp

backend - java BE
frontend - Angualar 15 (not working properly)

## Requirements

- apache-maven-3.8.6 or higher (or use `mvnw` provided in repo)
- JDK 17
- Spring Boot 3
- `docker-compose`
- Basic Authorization
- IDE of your choice
- postman collection

## architecture approach: hexagonal and ports & adapters

## Build

Execute:

`mvn clean package`


## Run

* Run all needed services via `docker-compose up -d`

* Verify all started `docker-compose logs -f`

* Build backend `mvn clean package`

* Run backend (class `GradBackendApplication`)

* defould user credentials:

      user: user
      pass: password
      role: user
    
      user: admin
      pass: password2
      role: user, admin

      user: editor
      pass: password3
      role: ALLOW_EDIT

* default app port:  `8991`

