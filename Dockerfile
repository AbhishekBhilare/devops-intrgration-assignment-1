FROM openjdk:8
EXPOSE 8080
ADD target/devops-jarfile.jar devops-jarfile.jar
ENTRYPOINT ["java","-jar","/devops-jarfile.jar"]
