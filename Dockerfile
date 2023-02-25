# فقط کافیست که دستور add و entrypoint درست شود تا معجزه رخ دهد

FROM maven:3.8.7-eclipse-temurin-17-alpine
#VOLUME /tmp
#COPY . .
#RUN mvn clean package
#EXPOSE 8080
ADD ./target/ASD_Backend_Server.jar ASD_Backend_Server.jar
#ENTRYPOINT ["java","-jar","./target/TaskManagement-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-jar","./ASD_Backend_Server.jar"]

