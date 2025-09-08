#Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

#Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/Examservice.jar exam-service.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "exam-service.jar"]