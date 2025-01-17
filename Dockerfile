FROM maven:4.0.0-openjdk-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 3002

ENTRYPOINT ["java", "-jar", "app.jar"]
