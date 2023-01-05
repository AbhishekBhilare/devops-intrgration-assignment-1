def user
node {
  wrap([$class: 'BuildUser']) {
    user = env.BUILD_USER_ID
  }
emailext body: '\'\'\'<h1>All is set to deployment please approve this steps to contiue deployment </h1><a href="${BUILD_URL}input">click to approve</a>\'\'\'', 
subject: "[Jenkins]${currentBuild.fullDisplayName}",
 to: 'abhishekbhilarea.b@gmail.com'
}


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
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
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
        stage('deploy') {
            input {
                message "Should we continue?"
                ok "Yes"
            }
            when {
                expression { user == 'hardCodeApproverJenkinsId'}
            }
            steps {
                sh "echo 'describe your deployment' "
            }
        }
        stage ('docker image to docker hub '){
            steps {
                script {
                    emailext body: 'Project is in deployment stage and all tesing and Qa is done', subject: 'Regarding the devlpoment', to: 'abhishekbhilarea.b@gmail.com'
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
