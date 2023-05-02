pipeline {
    agent any
    environment {
        REPO_URL = 'git@github.com:nishantindorkar/student-ui.git'
        SONARQUBE_ENV = 'sonarqube-new'
        IMG_NAME = 'tomcat-img'
        AWS_REGION = "us-east-1"
        AWS_ACCOUNT_ID = "164358940697"
        ECR_REPO_NAME = "tomcat-repo"
        IMG_TAG = 'latest'
    }
    stages {
        stage('Git Pull') {
            steps {
                sh 'sudo apt update -y'
                git credentialsId: 'jenkins', url: "${REPO_URL}"
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
                withSonarQubeEnv('sonarqube-new') {
                sh" mvn sonar:sonar \
                ${SCANNER_HOME**}/bin/sonar-scanner \
                -Dsonar.projectKey=project-dev-integrate \
                -Dsonar.host.url=http://18.208.136.77:9000 \
                -Dsonar.login=898ac8d0864105b15fa3b6c4cd4e6a8287c20c4b
                -Dsonar.sources=. "
            }
                // withSonarQubeEnv("${SONARQUBE_ENV}") {
                //     sh 'mvn sonar:sonar \
                //         -Dsonar.projectKey=project-dev-integrate \
                //         -Dsonar.host.url=http://18.208.136.77:9000 \
                //         -Dsonar.login=898ac8d0864105b15fa3b6c4cd4e6a8287c20c4b'
                // }
            }
        }
        // stage('Push Artifacts') {
        //     steps{
        //         // sh 'curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
        //         // sh 'unzip awscliv2.zip'
        //         // sh 'sudo ./aws/install'
        //         sh 'aws s3 mb s3://artifact-studentui'
        //         sh 'aws s3 cp **/*.war s3://artifact-studentui/student.war'
        //     }
        // }
        // stage('Checkout repository') {
        //     steps {
        //         checkout([$class: 'GitSCM',
        //                   branches: [[name: '*/main']],
        //                   doGenerateSubmoduleConfigurations: false,
        //                   extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]],
        //                   submoduleCfg: [],
        //                   userRemoteConfigs: [[credentialsId: 'jenkins', url: 'git@github.com:nishantindorkar/tf-module-project-k8.git']]])
        //     }
        // }
        // stage('Docker Build') {
        //     steps{
        //         // sh ''' #install docker commands
        //         // sudo apt update -y
        //         // sudo apt install apt-transport-https ca-certificates curl gnupg lsb-release -y
        //         // curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
        //         // echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
        //         // sudo apt update -y
        //         // sudo apt install docker-ce docker-ce-cli containerd.io -y
        //         // docker --version
        //         // echo "successfully installed"
        //         // '''
        //         //sh 'sudo usermod -aG docker $(whoami)' //add jenkins user to docker group
        //         sh 'docker builder prune --all && docker image prune --all && docker container prune --force'
        //         sh "docker build -t ${IMG_NAME}:${IMG_TAG} -f ${WORKSPACE}/docker/Dockerfile ."
        //         sh 'docker images'               
        //     }
        // }
        // stage('Push Docker Image to ECR') {
        //     steps {
        //         script {
        //             sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
        //             sh "docker tag ${IMG_NAME}:${IMG_TAG} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMG_TAG}"
        //             sh "docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMG_TAG}"
        //         }
        //     }
        // }
        // stage('Deployment') {
        //     steps {
        //         withCredentials([sshUserPrivateKey(credentialsId: 'ubuntu-machine', keyFileVariable: 'ubuntu', usernameVariable: 'tomcat')]) {
        //         sh '''
        //         ssh -i ${ubuntu} -o StrictHostKeyChecking=no ubuntu@44.212.21.84<<EOF
        //         sudo kubectl apply -f /var/lib/jenkins/workspace/project-test-phase/my-app.yaml
        //         sudo kubectl get pods
        //         sudo kubectl get svc
        //         '''
        //         }
        //     }
        // }
        // stage('Post-build Cleanup') {
        //     steps {
        //         //sh "aws ecr batch-delete-image --repository-name ${ECR_REPO_NAME} --image-ids imageTag=${IMG_TAG}"
        //         //sh 'mvn clean'
        //         //sh 'docker rmi -f `docker images -q`'
        //         sh 'sudo rm -rf target'
        //         sh 'ls -la'
        //     }
        // } 
    }
}
