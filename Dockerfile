FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cache dependencies first
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY src ./src
RUN mvn -B -ntp clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN useradd --system --uid 1001 spring
USER spring

COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
