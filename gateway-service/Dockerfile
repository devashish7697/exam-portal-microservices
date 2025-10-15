FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/gateway-service.jar gateway-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","gateway-service.jar"]