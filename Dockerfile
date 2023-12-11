FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./target/youdown-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
RUN ["mkdir", "media"]
CMD ["java", "-jar", "app.jar"]
