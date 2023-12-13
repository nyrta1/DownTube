FROM openjdk:17-jdk-alpine
COPY ./target/youdown-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

RUN apk update && apk upgrade && apk add --no-cache ffmpeg

CMD ["java", "-jar", "app.jar"]