FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests
FROM amazoncorretto:17
VOLUME /tmp
COPY --from=build /app/target/*.jar adt-dv-administration.jar
ENTRYPOINT ["java","-jar","adt-dv-administration.jar"]
