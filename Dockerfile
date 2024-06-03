FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/present-service-0.0.1-SNAPSHOT.jar /app/present-service.jar

CMD ["java", "-jar", "present-service.jar"]