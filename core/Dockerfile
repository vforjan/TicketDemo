FROM openjdk:8-jdk-alpine
EXPOSE 8082
ARG JAR_FILE=target/core-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} core.jar
ENTRYPOINT ["java","-jar","/core.jar"]