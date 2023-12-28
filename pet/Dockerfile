# CREATE A JAR FOR THE PROJECT

FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

# EXECUTE THE PREVIOUSLY CREATED JAR

FROM openjdk:17-slim

WORKDIR /usr/app

COPY --from=builder /app/target/pet-0.0.1-SNAPSHOT.jar pet-service.jar
COPY --from=builder /app/src/main/resources ./resources

EXPOSE 8081

CMD ["java", "-jar", "pet-service.jar"]
