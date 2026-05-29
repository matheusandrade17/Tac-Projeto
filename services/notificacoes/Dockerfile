# build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY services/notificacoes/pom.xml ./services/notificacoes/pom.xml
COPY services/notificacoes/src ./services/notificacoes/src
COPY services/contracts/pom.xml ./services/contracts/pom.xml
COPY services/contracts/src ./services/contracts/src
RUN mvn -f services/contracts/pom.xml clean install -DskipTests \
    && mvn -f services/notificacoes/pom.xml -DskipTests package

# runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/services/notificacoes/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
