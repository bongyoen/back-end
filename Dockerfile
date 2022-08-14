FROM openjdk:8-jdk-alpine
VOLUME ["/var/log"]
ARG JAR_FILE=build/libs/ec2spring-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]


