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
          IMAGE_NAME = "front-end:latest"
          IMAGE_STORAGE = "ec2-user"
          IMAGE_STORAGE_CREDENTIAL = "ContainerRegistry"
          SSH_CONNECTION = "ec2_keypair.pem ec2-user@13.56.107.91"
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

    stage('Push Container Image') {
      steps {
        script {
          docker.withRegistry("https://${IMAGE_STORAGE}", IMAGE_STORAGE_CREDENTIAL) {
            image.push("${env.BUILD_NUMBER}")
            image.push("latest")
          }
        }
      }
    }

    stage('Server Run') {
      steps {
        sshagent(credentials: [SSH_CONNECTION_CREDENTIAL]) {
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rm -f ${IMAGE_NAME}'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rmi -f ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker pull ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker images'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker run -d --name ${IMAGE_NAME} -p 8080:8080 ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker ps'"
        }

      }
    }

  }
}