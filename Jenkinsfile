pipeline {
    agent any
// ssh -i "ec2_keypair.pem" ec2-user@ec2-13-56-107-91.us-west-1.compute.amazonaws.com
// ec2-13-56-107-91.us-west-1.compute.amazonaws.com
    stages {
        stage("Set Variable") {
            steps {
                script {
                    IMAGE_NAME = "front-end"
                    IMAGE_STORAGE = "13.56.107.91"
                    IMAGE_STORAGE_CREDENTIAL = "ContainerRegistry"
                    SSH_CONNECTION = "ec2_keypair.pem ec2-user@13.56.107.91'"
                    SSH_CONNECTION_CREDENTIAL = "Deploy-Server-SSH-Crednetial"
                }
            }
        }

//         stage("Clean Build Test") {
//             steps {
//                 sh "./gradlew clean build test"
//             }
//         }

        stage("Build Container Image") {
            steps {
                script {
                    image = docker.build("${IMAGE_STORAGE}/${IMAGE_NAME}")
                }
            }
        }

        stage("Push Container Image") {
            steps {
                script {
                    docker.withRegistry("https://${IMAGE_STORAGE}", IMAGE_STORAGE_CREDENTIAL) {
                        image.push("${env.BUILD_NUMBER}")
                        image.push("latest")
                        image
                    }
                }
            }
        }

        stage("Server Run") {
            steps {
                sshagent([SSH_CONNECTION_CREDENTIAL]) {
                    // 최신 컨테이너 삭제
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rm -f ${IMAGE_NAME}'"
                    // 최신 이미지 삭제
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rmi -f ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
                    // 최신 이미지 PULL
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker pull ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
                    // 이미지 확인
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker images'"
                    // 최신 이미지 RUN
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker run -d --name ${IMAGE_NAME} -p 8080:8080 ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
                    // 컨테이너 확인
                    sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker ps'"
                }
            }
        }
    }

}