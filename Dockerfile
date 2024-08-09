FROM openjdk:17-alpine
WORKDIR /app
COPY ./target/lesson-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "lesson-service-0.0.1-SNAPSHOT.jar"]