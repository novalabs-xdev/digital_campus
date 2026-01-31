# =========================
# STAGE 1 : Build
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# =========================
# STAGE 2 : Runtime
# =========================
FROM eclipse-temurin:21-jre

LABEL authors="nelsam"

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render injecte PORT automatiquement
EXPOSE 8080

# JVM optimisée pour container (local + cloud)
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]

