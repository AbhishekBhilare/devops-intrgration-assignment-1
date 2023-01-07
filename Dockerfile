FROM openjdk:8
EXPOSE 8080
ADD target/my-app-1.0-snapshot.jar my-app-1.0-snapshot.jar
ENTRYPOINT ["java","-jar","/my-app-1.0-snapshot.jar"]
