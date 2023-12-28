# Folosește o imagine oficială Maven pentru construirea aplicației
FROM maven:3.8.4-openjdk-17 AS builder

# Setează directorul de lucru în cadrul containerului
WORKDIR /app

# Copiază fișierele POM și codul sursă în directorul curent
COPY pom.xml .
COPY src ./src

# Construiește proiectul folosind Maven
RUN mvn clean package

# Folosește o imagine openjdk pentru a executa aplicația
FROM openjdk:17-slim

# Setează directorul de lucru în cadrul containerului
WORKDIR /usr/app

# Copiază JAR-ul construit din stadiul anterior în directorul curent
COPY --from=builder /app/target/users-0.0.1-SNAPSHOT.jar users-service.jar
COPY --from=builder /app/src/main/resources ./resources

# Expose portul pe care rulează serviciul
EXPOSE 8083

# Comandă pentru a executa serviciul când containerul este pornit
CMD ["java", "-jar", "users-service.jar"]
