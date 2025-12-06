## docker build --build-arg target/app.jar .
# docker compose build
FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /home/app

# Copy Maven structure
COPY pom.xml /home/app/pom.xml

COPY aviation-api-service/pom.xml /home/app/aviation-api-service/pom.xml
COPY aviation-api-service/src /home/app/aviation-api-service/src

COPY airport-client/pom.xml /home/app/airport-client/pom.xml
COPY airport-client/src /home/app/airport-client/src

COPY airport-client-dto/pom.xml /home/app/airport-client-dto/pom.xml
COPY airport-client-dto/src /home/app/airport-client-dto/src

# Build the project
RUN mvn clean package -DskipTests

# ---- Runtime ----
FROM eclipse-temurin:25-jre AS runtime

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /home/app

COPY --from=build /home/app/aviation-api-service/target/aviation-api-service*.jar app.jar

EXPOSE 8092
ENTRYPOINT ["java","-jar","app.jar"]