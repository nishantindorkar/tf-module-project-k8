pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                sh 'sudo apt update -y'
                git credentialsId: 'jenkins', url: 'git@github.com:nishantindorkar/student-ui.git'
                // sh 'pwd'
                // sh 'ls'
            }
        }
        stage("build-maven") {
            steps { 
                sh 'sudo apt-get update -y'
                sh 'sudo apt-get install maven curl unzip -y'
                sh 'mvn clean package'
            }
        } 
        // stage('SonarQube Analysis') {
        //     steps {
        //         withSonarQubeEnv('sonarqube-sonar') {
        //             sh 'gradle sonarqube'
        //         }
        //     }
        // }
    }
}
