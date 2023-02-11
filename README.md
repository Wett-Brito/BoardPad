# Boardpad - Backend

A `Spring Boot Application` that provide resources to [Boardpad - Frontend](https://github.com/Wett-Brito/Boardpad-front)

---

### Involved Technologies

- Java 11
- Spring Boot
- Gradle 7.3
- Mapstruct and Lombok
- Swagger UI
- MariaDB/MySQL:Latest

### How to Setup

Starts your **MySQL** or **MariaDB** database and create a schema named as **boardpad_db**. (Don't forget to put
the correct database **user** and **password** at _application.properties_ file)

Change the `spring.jpa.hibernate.ddl-auto` from **update** to **create-drop** at _application.properties_ file, to the 
**hibernate** auto-create the database tables and fields.

Run `./gradlew bootRun` on **boardpad-backend** path.

---