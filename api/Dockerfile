FROM openjdk:8-jdk-alpine
EXPOSE 8081
ARG JAR_FILE=target/api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} api.jar
ENTRYPOINT ["java","-jar","/api.jar"]