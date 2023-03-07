# فقط کافیست که دستور add و entrypoint درست شود تا معجزه رخ دهد

FROM maven:3.8.7-eclipse-temurin-17-alpine
EXPOSE 8080
ADD ./target/ASD_Backend_Server.jar ASD_Backend_Server.jar
ENTRYPOINT ["java","-jar","./ASD_Backend_Server.jar"]
HEALTHCHECK --retries=5 --interval=30s CMD curl --f localhost:8080/health
