pipeline {
    agent any
    environment {
        REPO_URL = 'git@github.com:nishantindorkar/student-ui.git'
        SONARQUBE_ENV = 'sonarqube-sonar'
    }
    stages {
        stage('Git Pull') {
            steps {
                sh 'sudo apt update -y'
                git credentialsId: 'jenkins', url: "${REPO_URL}"
                // sh 'pwd'
                // sh 'ls'
            }
        }
        stage("Build Maven") {
            steps { 
                // sh 'sudo apt-get update -y'
                // sh 'sudo apt-get install maven curl unzip -y'
                sh 'mvn clean package'
            }
        } 
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}
