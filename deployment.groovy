pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                sh 'sudo apt update -y'
                git credentialsId: 'jenkins', url: 'git@github.com:nishantindorkar/student-ui.git'
                sh 'ls'
            }
        }
        stage("build-gradle") {
            steps { 
                sh 'sudo apt update -y'
                sh 'sudo apt-get install gradle curl unzip -y'
                sh 'gradle clean build'
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
