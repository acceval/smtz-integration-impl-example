node {
    withMaven(maven:'maven') {

        stage('Checkout') {
       
          git url: 'https://github.com/acceval/ms-util.git', 
            credentialsId: '3ce36dd4-c7ad-42bc-949a-e722c732e6cd', 
            branch: 'master'
        }
        
        stage('Build') {
            
            sh 'mvn clean install'
        }
    }
}