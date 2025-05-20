pipeline {
    agent any

    environment {
        IMAGE_NAME = "eminkck61/swe304:latest"
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/emikucuk/swe3043.git'
            }
        }
        stage('Build') {
            steps {
                bat 'gradlew.bat build'
            }
        }
        stage('Docker Build') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }
        stage('DockerHub Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                }
            }
        }
        stage('Docker Push') {
            steps {
                bat 'docker push %IMAGE_NAME%'
            }
        }
        stage('K8s Deploy') {
            steps {
                bat 'kubectl apply -f deployment.yaml --validate=false --kubeconfig=C:\\Users\\Erdem\\.kube\\config'
                bat 'kubectl apply -f service.yaml --validate=false --kubeconfig=C:\\Users\\Erdem\\.kube\\config'
            }
        }
    }
}
