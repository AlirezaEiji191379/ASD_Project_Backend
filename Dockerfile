FROM maven:3.8.7-eclipse-temurin-17-alpine
RUN echo "the base image was downloaded"
EXPOSE 8080
RUN echo "8080 port is exposed"
VOLUME /tmp
WORKDIR /app
#COPY target/*.jar app.jar
COPY . .
RUN mvn clean package
ENTRYPOINT ["java","-jar","./app/app.jar"]
#CMD mvn deploy