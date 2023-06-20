# First stage: build the application
FROM maven:3.8.3-jdk-11 AS build
COPY . /app
WORKDIR /app
RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

# Second stage: create a slim image
FROM openjdk:11-jre-slim
COPY --from=build /app/target/recipe*.jar /recipe-backend-api.jar
ENTRYPOINT ["java", "-jar", "/recipe-backend-api.jar"]