pipeline {
    agent any 
    tools {
        jdk 'java.home'
        maven 'Maven-3.8.7'
        dockerTool 'docker'
        
    }
    stages {
        stage('SonarQube analysis') {
    def scannerHome = tool 'sonar';
    withSonarQubeEnv('Sonar-Install') {
      sh "${scannerHome}/bin/sonar-scanner \
      -D sonar.login=admin \
      -D sonar.password=Abhishek@123 \
      -D sonar.projectKey=sonarqubetest \
      -D sonar.exclusions=vendor/**,resources/**,**/*.java \
      -D sonar.host.url=http://192.168.1XX.XX:9000/"
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
