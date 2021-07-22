FROM openjdk:8-jdk-alpine
EXPOSE 8084
ARG JAR_FILE=target/partner-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} partner.jar
ENTRYPOINT ["java","-jar","/partner.jar"]