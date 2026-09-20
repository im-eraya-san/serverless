def awsLogin(){

//  Take region from infraRegion file
    def REGION = infraRegion.infraRegion()

// Apply aws cred according to region
    withAWS(credentials: 'AWS', region: REGION){
        withEnv(["REGION=${REGION}"]){
            withCredentials([
                usernamePassword(
                    // Get userName and Password of ECR
                    credentialsId: 'ECR',
                    usernameVariable: 'uname',
                    passwordVariable: 'passwd'
                )
            ]){
                // Login to ECR
                sh 'aws ecr get-login-password --region "$REGION"| docker login --username "$uname" --password-stdin "$passwd"'
            }
        }
    }
}
