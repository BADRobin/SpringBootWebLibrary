FROM maven:3.8.5-openjdk-17 AS build

COPY . .

RUN mvn clean package -Dskiptest

FROM openjdk:17.0.1-jdk-slim

COPY --from=build /target/docker-render-0.0.1-SNAPSHOT.jar docker-render.jar

EXPOSE 8088

CMD ["java", "-jar", "docker-render.jar"]