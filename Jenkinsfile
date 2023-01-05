pipeline {
    agent any 
    tools {
        jdk 'java.home'
        maven 'Maven-3.8.7'
        dockerTool 'docker'
    }
    stages {
        stage ('Build maven project'){
            steps{
                bat 'docker --version'
                bat 'java -version'
                bat 'mvn -v'
                checkout scmGit(branches [[name 'master']], extensions [], userRemoteConfigs [[url 'httpsgithub.comAbhishekBhilareDevops-demo.git']])
                bat 'mvn clean install'
                echo '-------------------------build sucessfully done --------------------'
            }
        }
        stage ('docker image building'){
            steps {
                script{
                    echo '-----------------------docker iamge building started---------------------'
                    bat 'docker build -t devops-automationdevops-build-lastfile .'
                }   
            }
        }
        stage ('docker image to docker hub '){
            steps {
                script {
                    emailext body 'Project is in deploment satage  cahnges made by the developer', subject 'All test cases are done ready to deploy the project', to 'abhishekbhilarea.b@gmail.com'
                    echo '---------------------entered in the docker to docker hub-------------------- '
                    withCredentials([string(credentialsId 'DockerHubPWD', variable 'DockerHubPWD')]) {
                        bat 'docker login -u {AbhishekBhilare} -p {DockerHubPWD}'
                    }
                    bat 'docker push devops-automationdevops-build-lastfile '
                }
            }
        }
    }
}
