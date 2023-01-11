pipeline {
    agent any 
    tools {
        jdk 'java.home'
        maven 'Maven-3.8.7'
        dockerTool 'docker'
        
    }
    stages {
        stage('build && SonarQube analysis') {
            steps {
                withSonarQubeEnv('Sonar-Server') {
                    // Optionally use a Maven environment you've configured already
                    withMaven(maven:'Maven-3.8.7') {
                        bat 'mvn clean package sonar:sonar'
                    }
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {

                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage ('Building Docker Image'){
            steps {
                script{
                    echo '-----------------------docker iamge building started---------------------'
                     bat 'docker build -t abhishekbhilare/devops-jarfile .'
                }   
            }
        }
        stage ('Docker Image->Docker Hub'){
            steps {
                script {
                    echo '---------------------entered in the docker to docker hub-------------------- '
                    withCredentials([usernamePassword(credentialsId: 'f07f5cfa-9ff8-4c57-b198-32eb76dcd92c', passwordVariable: 'DOCKERHUBPWD', usernameVariable: 'DOCKERHUBUSERNAME')]) {
                        bat 'docker login --username=%DOCKERHUBUSERNAME%  --password=%DOCKERHUBPWD% '
                    }
                    echo "pushing docker image "
                    bat 'docker push abhishekbhilare/devops-jarfile'
                }
                }
            }
      
        stage ('Deployment') {
            steps{
                echo 'deployment'
                emailext body: 'Project is in deployment stage and deployment is done by the devloper', subject: 'Regarding the devlpoment', to: 'abhishekbhilarea.b@gmail.com'
            }
        }
        stage ('Production') {
            steps{
                echo 'production'
                bat 'docker pull abhishekbhilare/devops-jarfile:latest'
                bat 'docker run -p 8000:8000 abhishekbhilare/devops-jarfile:latest'
            }
        }
}
}
