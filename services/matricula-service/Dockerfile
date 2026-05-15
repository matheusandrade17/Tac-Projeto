FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn -f services/contracts/pom.xml clean install -DskipTests \
    && mvn -f services/matricula-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY --from=build /app/services/matricula-service/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]