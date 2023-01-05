FROM openjdk:8
EXPOSE 8080
ADD target/devops-build-lastfile.jar devops-build-lastfile.jar
ENTRYPOINT ["java","-jar","/devops-build-lastfile.jar"]
