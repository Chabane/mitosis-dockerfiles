pipelineJob("nodejs-pipeline") {
    definition {
        cps {
            sandbox()
            script("""
                node {
                      sh 'nvm use 6'

                        stage ("checkout") {
                            git url : 'https://github.com/NirbyApp/mitosis-microservice-nodejs-angular.git'
                        }
 
                        stage ("install") {
                            sh 'npm install -g yarn'
                            sh 'yarn'
                        }
                          
                        stage ("test") {
                            sh 'npm run test -watch=false'
                        }
                       
                        stage ("build") {
                            sh 'npm run build'
                        }
                       
                        stage ("deploy"){
                            sh 'docker build -t mitosis/microservice-nodejs .'
                            sh 'docker service create --name microservice-nodejs --publish 9992:80 --network microservices-net --replicas 2 mitosis/microservice-nodejs'
                        }                      
                }             
            """.stripIndent())
        }
    }
}
