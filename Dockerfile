FROM openjdk:17
MAINTAINER sergey sundukovskiy
COPY target/se300-final-0.0.1-SNAPSHOT.jar se300-final-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/se300-final-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080