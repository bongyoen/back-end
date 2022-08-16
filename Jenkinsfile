pipeline {
  agent any
  stages {
    stage('Clean Build Test') {
      steps {
        sh 'chmod +x gradlew'
        sh './gradlew clean build test'
      }
    }

    stage('Set Variable') {
      steps {
        script {
          IMAGE_NAME = "back-end"
          IMAGE_STORAGE = "ec2-user"
          IMAGE_STORAGE_CREDENTIAL = "ContainerRegistry"
          SSH_CONNECTION = "ec2-user@13.56.107.91"
          SSH_CONNECTION_CREDENTIAL = "Deploy-Server-SSH-Crednetial"
        }

      }
    }

    stage('Build Container Image') {
      steps {
        script {
          image = docker.build("${IMAGE_STORAGE}/${IMAGE_NAME}")
        }

      }
    }

    stage('Server Run') {
      steps {
        sshagent(credentials: [SSH_CONNECTION_CREDENTIAL]) {
            script {
                try {
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker stop ${IMAGE_NAME}'"
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rm -f ${IMAGE_NAME}'"
                } catch (e) {
                    sh 'echo "fail to stop and remove container"'
                }
            }
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker run -d --name ${IMAGE_NAME} -p 8080:8080 ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker image prune -a'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'echo y'"
        }

      }
    }

  }
}