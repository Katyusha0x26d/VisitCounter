FROM alpine/java:21-jdk

WORKDIR /app
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]