FROM maven:3.8.7-eclipse-temurin-17-alpine
#VOLUME /tmp
COPY . .
RUN mvn clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","./target/TaskManagement-0.0.1-SNAPSHOT.jar"]