@Library('ECR') _

// global var to load configuration
def cfg

pipeline {
// build on any available agent
    agent any

// env var for the local build image name
    environment{ LOCAL_IMAGE_NAME = "serverless/ecs:${env.BUILD_ID}" }

    stages {
        stage("Configuring Environment | Load Region"){
            steps{
                script{
                    // Load default region
                    infraRegion.infraRegion()               
                }
            }
        }

        stage("Configuring Environment | Login to ECR"){
            steps{
                script{
                // login to aws ECR
                    awsLogin.awsLogin()
                }
            }
        }

        stage("Create local image"){
            steps{
               script{
                    sh 'docker build -t $LOCAL_IMAGE_NAME .'
                    tag(env.LOCAL_IMAGE_NAME)
               }
            }
        }

        stage("Pushing to ECR"){
            steps{
                script{
                    tag.push()
                }
            }
        }

        stage("Cleaning image after push to ECR"){
            steps{
                script{
                    tag.clean()
                }
            }
        }


    }
}
