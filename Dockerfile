# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven files first (for caching)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source and build
COPY src src
RUN ./mvnw package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the final jar from the build stage
COPY --from=build /app/target/pixzeleria-backend-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Optional: healthcheck for Railway
HEALTHCHECK --interval=30s --timeout=10s CMD curl -f http://localhost:8080/actuator/health || exit 1
