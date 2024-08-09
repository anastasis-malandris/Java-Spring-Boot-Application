# Overview

Lesson microservice with springboot/maven and a postgres database.

# Connect to Database

Run ```docker-compose up -d``` to start the database containers and on localhost:5050 you will be able to connect to the postgres database. Username/password can be found on the .env file.

If it is the first time doing that, you will have to create a new server named admin and a database named lesson.
# Package the Microservice

Run ````mvn clean package````

# Run Tests

Run ```mvn clean verify```