FROM maven:3.8.7-eclipse-temurin-17-alpine
EXPOSE 8080
ADD ./target/ASD_Backend_Server.jar ASD_Backend_Server.jar
COPY ./wait-for-it.sh .
RUN chmod 777 ./wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh" ,"--timeout=300","--host=db" , "--port=3306" , "-s" ,"--", "java" ,"-jar","./ASD_Backend_Server.jar"]