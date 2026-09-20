def infraRegion(){
   
//    This function takes aws region from Jenkins cred store
//    it is for security purpose.
   
    def region 
    withCredentials([
        string(credentialsId: 'infraRegion', variable: 'Region')
    ]){region = env.Region}

    return region
}
