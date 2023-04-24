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
    }
}
