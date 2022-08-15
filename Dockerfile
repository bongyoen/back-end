#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE=build/libs/ec2spring-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","/app.jar"]




FROM amazoncorretto:11-alpine-jdk
#MAINTAINER DevBeekei <devbeekei.shin@gmail.com>

EXPOSE 8080

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
CMD java -jar ./deploy-app.jar