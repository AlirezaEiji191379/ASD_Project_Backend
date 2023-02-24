FROM maven:3.8.7-eclipse-temurin-17-alpine
VOLUME /tmp
COPY . .
CMD mvn deploy