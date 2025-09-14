#build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

#run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/user-service.jar user-service.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","user-service.jar"]
