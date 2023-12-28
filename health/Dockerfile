# CREATE A JAR FOR THE PROJECT

FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

# EXECUTE THE PREVIOUSLY CREATED JAR

FROM openjdk:17-slim

WORKDIR /usr/app

COPY --from=builder /app/target/health-0.0.1-SNAPSHOT.jar health-service.jar
COPY --from=builder /app/src/main/resources ./resources

EXPOSE 8080

CMD ["java", "-jar", "health-service.jar"]
