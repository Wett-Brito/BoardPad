# Boardpad - Backend

That's a `Spring Boot` backend application that provides resources to Boardpad Angular app.

If you want to see more details of Boardpad functionalities and how it works, please visit the 
[Boardpad Frontend](https://github.com/GustavoReinaldi/Boardpad-Front) repository.

---

### Involved Technologies

- Java 11
- Spring Boot
- Gradle 7.3
- Mapstruct and Lombok
- Swagger UI
- MariaDB/MySQL:Latest

### How to Setup

---
You can start/create your own **MySQL** database server, or you can let the 
`Docker` do all the hard word for you.

#### Manual Way

- Start your local database or choose the datasource changing the existing credentials (`url`, `user` and `password`) at `application.properties` file.
- You can create a **database/schema** named as **boardpad_db** or let the application do this for you.
- Run `./gradlew clean build bootRun` on **boardpad-backend** path.

#### Docker way
Run the following commands on prompt:
- Build the application with `./gradlew clean build`
- Start up **MySQL** and the **Spring Boot** app using the command `docker-compose -p [CONTAINER_NAME] up -d`

After configuring the application, the Swagger will be available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 

---