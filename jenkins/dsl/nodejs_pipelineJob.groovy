pipelineJob("nodejs-pipeline") {
    definition {
        cps {
            sandbox()
            script("""
                node {
                    docker.image('node:boron').inside {

                            def retstat = sh(script: 'docker service inspect microservice-nodejs', returnStatus: true)

                            stage ("checkout") {
                                git url : 'https://github.com/NirbyApp/mitosis-microservice-nodejs-angular.git'
                            }

                            stage ("install") {
                                sh 'yarn'
                            }

                            stage ("test") {
                            }

                            stage ("build") {
                                sh 'npm run build:prod'
                            }

                            stage ("deploy"){
                                sh 'docker build -t mitosis/microservice-nodejs .'
                                if (retstat == 1) {
                                    sh 'docker service create --name microservice-nodejs --publish 9992:80 --network microservices-net --replicas 2 mitosis/microservice-nodejs'
                                } else {
                                    sh 'docker service update --replicas 2 --image mitosis/microservice-nodejs microservice-nodejs'
                                }    
                            }       
                        }                             
                    }             
            """.stripIndent())
        }
    }
}
