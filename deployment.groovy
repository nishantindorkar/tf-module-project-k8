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
        stage("build-gradle") {
            steps { 
                // sh 'sudo apt update -y'
                // sh 'sudo apt-get install gradle curl unzip -y'
                sh 'cd /var/lib/jenkins/workspace/project-test-phase && /usr/bin/gradle clean build'
                sh 'gradle --version'
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
