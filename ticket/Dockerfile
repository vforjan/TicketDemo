FROM openjdk:8-jdk-alpine
EXPOSE 8083
ARG JAR_FILE=target/ticket-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} ticket.jar
ENTRYPOINT ["java","-jar","/ticket.jar"]