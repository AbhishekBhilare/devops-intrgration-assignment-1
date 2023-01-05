pipeline {
    agent any 
    tools {
        jdk 'java.home'
        maven 'Maven-3.8.7'
        dockerTool 'docker'
        
    }
    stages {
            stage('Scan') {
      steps {
        withSonarQubeEnv(installationName: 'Sonar-Install') { 
          sh './mvnw clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
        }
      }
    }
    stage("Quality Gate") {
      steps {
        timeout(time: 2, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
        stage ('Build maven project'){
            steps{
                bat 'docker --version'
                bat 'java -version'
                bat 'mvn -v'
                checkout scmGit(branches: [[name: '*/devlopment']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/AbhishekBhilare/Devops-demo.git']])
                bat 'mvn clean install'
                echo '-------------------------build sucessfully done --------------------'
            }
        }
        stage ('docker image building'){
            steps {
                script{
                    echo '-----------------------docker iamge building started---------------------'
                    bat 'docker build -t abhishekbhilare/devops-automationdevops-build-lastfile .'
                }   
            }
        }
        stage ('docker image to docker hub '){
            steps {
                script {
                    emailext body: 'Project is in deployment stage and deployment is done by the devloper', subject: 'Regarding the devlpoment', to: 'abhishekbhilarea.b@gmail.com'
                    echo '---------------------entered in the docker to docker hub-------------------- '
                    withCredentials([usernamePassword(credentialsId: 'f07f5cfa-9ff8-4c57-b198-32eb76dcd92c', passwordVariable: 'DOCKERHUBPWD', usernameVariable: 'DOCKERHUBUSERNAME')]) {
                        bat 'docker login --username=%DOCKERHUBUSERNAME%  --password=%DOCKERHUBPWD% '
                    }
                    echo "pushing docker image "
                    bat 'docker push abhishekbhilare/devops-automationdevops-build-lastfile'
                }
            }
        }
    }
}
