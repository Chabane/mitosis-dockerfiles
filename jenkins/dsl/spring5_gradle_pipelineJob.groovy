pipelineJob('spring5-gradle-pipeline') {
    definition {
        cps {
            sandbox()
            script("""
                node {
                      def retstat = sh(script: 'docker service inspect microservice-spring-reactor', returnStatus: true)

                      stage ('checkout') {
                        git url : 'https://github.com/NirbyApp/mitosis-microservice-spring-reactor.git'
                      }     
                      stage ('test') {
                        sh './gradlew test'
                      }

                      stage ('build') {
                        sh './gradlew build'
                      }

                      stage ('deploy') {
                        sh 'docker build -t mitosis/microservice-spring-reactor .'
                        if (retstat == 1) {
                            sh 'docker service create --name microservice-spring-reactor --publish 9991:8080 --network microservices-net --replicas 2 mitosis/microservice-spring-reactor'  
                        } else {
                            sh 'docker service update --replicas 2 --image mitosis/microservice-spring-reactor microservice-spring-reactor'
                        }            
                     }
                }                 
            """.stripIndent())
        }
    }
}