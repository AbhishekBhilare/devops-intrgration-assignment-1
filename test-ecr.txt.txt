pipeline {
    agent any 
    // for ecr
    environment {
        registry = '272814864966.dkr.ecr.us-east-1.amazonaws.com/devops-assignment-02'
        registryCredential = '0a558f0b-6652-4705-b322-d8a11409a5aa'
        dockerImage = 'Myimage'
    }
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
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage ('Building Docker Image'){
            steps {
                script{
                    echo '-----------------------docker iamge building started---------------------'
                    bat 'docker build -t devops-assignment-02 .'
                }   
            }
        }
        stage ('Docker Image->Docker Hub'){
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'f07f5cfa-9ff8-4c57-b198-32eb76dcd92c', passwordVariable: 'DOCKERHUBPWD', usernameVariable: 'DOCKERHUBUSERNAME')]) {
                        bat 'docker login --username=%DOCKERHUBUSERNAME%  --password=%DOCKERHUBPWD% '
                    }
                    echo "pushing docker image "
                    bat 'docker tag devops-assignment-02:latest 272814864966.dkr.ecr.us-east-1.amazonaws.com/devops-assignment-02:latest'
//                     bat 'docker push devops-assignment-02:latest'
                    //for ecr
                    docker.withRegistry("https://" + registry, "ecr:us-east-1:" + registryCredential) {
                        bat 'docker push 272814864966.dkr.ecr.us-east-1.amazonaws.com/devops-assignment-02:latest'
                }
                }
            }
        }
        stage ('Deployment') {
            steps{
                echo 'deployment'
                emailext body: 'Project is in deployment stage and deployment is done by the devloper', subject: 'Regarding the devlpoment', to: 'abhishekbhilarea.b@gmail.com'
            }
        }
    }
}