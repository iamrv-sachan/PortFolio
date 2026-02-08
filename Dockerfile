FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

# Build ONLY the server module
# Explicitly skip Android & shared builds to avoid SDK issues
RUN ./gradlew :server:build \
    -x :composeApp:build \
    -x :shared:build \
    --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/server/build/libs/server-all.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
