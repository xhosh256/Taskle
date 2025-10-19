# Step 1
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /taskle
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2
FROM eclipse-temurin:21-jre-alpine
WORKDIR /taskle
COPY --from=build /taskle/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]