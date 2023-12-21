FROM maven:3.8.5-openjdk-17 AS build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

RUN mvn clean package  -Dskiptests

FROM openjdk:17.0.1-jdk-slim

RUN mkdir /uploads

COPY --from=build /target/zion23182-0.0.1-SNAPSHOT.jar zion23182.jar

EXPOSE 9094

ENTRYPOINT [ "java","-jar","zion23182.jar"]
