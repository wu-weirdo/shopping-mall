FROM openjdk:8-jdk-alpine
MAINTAINER tan66
VOLUME /tmp
ARG ./shopping-web-1.0.0-SNAPSHOT.jar
COPY ./shopping-web-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]