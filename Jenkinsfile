pipeline {
    agent any

    environment {
        IMAGE_NAME = "swe304"
        DOCKERHUB_USER = "eminkck61" 
        DOCKERHUB_PASSWORD = "6161.Tsch"
        KUBE_CONFIG = "C:\\Users\\emink\\.kube\\config"
        KUBECONFIG = "C:\\Users\\emink\\.kube\\config"
    }

    triggers {
        pollSCM('* * * * *') 
    }

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/emikucuk/swe3043.git'
            }
        }
        stage('Build JAR') {
            steps {
                bat './gradlew.bat build -x test'
            }
        }
        stage('Docker Build') {
            steps {
                bat "docker build -t %DOCKERHUB_USER%/%IMAGE_NAME%:latest ."
            }
        }
        stage('Docker Login') {
            steps {
                bat "docker login -u %DOCKERHUB_USER% -p %DOCKERHUB_PASSWORD%"
            }
        }
        stage('Push to DockerHub') {
            steps {
                bat "docker push %DOCKERHUB_USER%/%IMAGE_NAME%:latest"
            }
        }
        stage('K8s Deploy') {
            steps {
                bat 'set KUBECONFIG=%KUBE_CONFIG% && kubectl apply -f deployment.yaml --validate=false'
                bat 'set KUBECONFIG=%KUBE_CONFIG% && kubectl apply -f service.yaml --validate=false'
            }
        }
    }
}
