pipeline {
    agent any
    environment {
        REPO_URL = 'git@github.com:nishantindorkar/student-ui.git'
        SONARQUBE_ENV = 'sonarqube-new'
        DOCKERFILE_PATH = './docker'
        IMAGE_NAME = 'img-dev'
        TAG = 'latest'
    }
    stages {
        stage('Git Pull') {
            steps {
                sh 'sudo apt update -y'
                git credentialsId: 'jenkins', url: "${REPO_URL}"
                // sh 'pwd'
                //sh 'ls'
            }
        }
        stage("Build Maven") {
            steps { 
                // sh 'sudo apt-get update -y'
                // sh 'sudo apt-get install maven curl unzip -y'
                sh 'mvn clean package'
            }
        }
        // stage('SonarQube Analysis') {
        //     steps {
        //         withSonarQubeEnv("${SONARQUBE_ENV}") {
        //             sh 'mvn sonar:sonar \
        //                 -Dsonar.projectKey=project-dev-integrate \
        //                 -Dsonar.host.url=http://44.201.139.163:9000 \
        //                 -Dsonar.login=898ac8d0864105b15fa3b6c4cd4e6a8287c20c4b'
        //         }
        //     }
        // }
        stage('Push Artifacts') {
            steps{
                // sh 'curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
                // sh 'unzip awscliv2.zip'
                // sh 'sudo ./aws/install'
                sh 'aws s3 mb s3://artifact-studentui'
                sh 'aws s3 cp **/*.war s3://artifact-studentui/student-${BUILD_ID}.war'
            }
        }
        stage('Docker Build') {
            steps{
                // sh ''' #install docker commands
                // sudo apt update -y
                // sudo apt install apt-transport-https ca-certificates curl gnupg lsb-release -y
                // curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
                // echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
                // sudo apt update -y
                // sudo apt install docker-ce docker-ce-cli containerd.io -y
                // docker --version
                // echo "successfully installed"
                // '''
                //sh 'sudo usermod -aG docker $(whoami)'
                git credentialsId: 'jenkins', url: 'git@github.com:nishantindorkar/tf-module-project-k8.git'
                sh 'pwd'
                sh 'docker build -t img-dev -f ${pwd} .'
                sh 'docker images'                
                // script {
                //     docker.build("${IMAGE_NAME}:${TAG}", "-f ${DOCKERFILE_PATH} .")
                //     docker.image("${IMAGE_NAME}:${TAG}").inspect()
                // }
            }
        }
        stage('Post-build Cleanup') {
            steps {
                //sh 'mvn clean'
                sh 'sudo rm -rf target'
            }
        } 
    }
}
