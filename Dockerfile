
FROM jelastic/maven:3.9.5-openjdk-21 AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package

FROM eclipse-temurin:21.0.2_13-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/target/testealura-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]