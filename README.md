# CityApp

## Requirements

- apache-maven-3.8.6 or higher (or use `mvnw` provided in repo)
- JDK 17
- Spring Boot 3
- `docker-compose`
- Basic Authorization
- IDE of your choice
- postman collection

## architecture approach: hexagonal and ports & adapters
## Testcontainers

## Build

Execute:

`mvn clean package`


## Run

* Run all needed services via `docker-compose up -d`

* Verify all started `docker-compose logs -f`

* Build backend `mvn clean package`

* Run backend (class `GradBackendApplication`)

* default user credentials:

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

* app.pull.csv.file=true set to false after first start
