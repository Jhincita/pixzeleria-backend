# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven files first (for caching)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy source and build
COPY src src
RUN ./mvnw package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the final jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
