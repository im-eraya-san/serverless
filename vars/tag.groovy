def call(String LOCAL_IMAGE_NAME){
    withCredentials([
        string(credentialsId: 'AC-ID', variable: 'imgName')
    ]){
        
        sh "docker image tag ${LOCAL_IMAGE_NAME} \${imgName}:${env.BUILD_ID}"
    }
}
