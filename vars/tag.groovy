def call(String LOCAL_IMAGE_NAME){
    withCredentials([
        string(credentialsId: 'AC-ID', variable: 'imgName')
    ]){ 
        sh "echo $LOCAL_IMAGE_NAME $imgName
        sh "docker image tag ${LOCAL_IMAGE_NAME} \${imgName}:${env.BUILD_ID}"
    }
}

def push(){
    withCredentials([
        string(credentialsId: 'AC-ID', variable: 'imgName')
    ]){ 
        sh "docker push ${imgName}:${env.BUILD_ID}"
    }
}


def clean(){
    withCredentials([
        string(credentialsId: 'AC-ID', variable: 'imgName')
    ]){ 
        sh "docker rmi -f ${imgName}:${env.BUILD_ID}"
    }
}
