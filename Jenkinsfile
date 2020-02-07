node {
    withMaven(maven:'maven') {

        stage('Checkout') {
       
          git url: 'https://github.com/acceval/ms-util.git', 
            credentialsId: '9b47d777-3343-4e6b-a23b-dc0d0a28df58', 
            branch: 'develop'
        }
        
        stage('Build') {
            
            sh 'mvn clean install'
        }
    }
}
