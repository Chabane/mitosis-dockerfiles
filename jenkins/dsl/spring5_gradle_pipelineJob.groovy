pipelineJob("spring5-gradle-pipeline") {
    definition {
        cps {
            sandbox()
            script("""
                node {
                      stage "checkout"
                       git url : 'https://github.com/NirbyApp/mitosis-microservice-spring-reactor.git'
                           
                      stage "test"
                       sh './gradlew test'
                      
                      stage "build"
                       sh './gradlew build'

                      stage "deploy"
                       sh 'docker build -t mitosis/microservice-spring-reactor .'
                       sh 'docker service create --name microservice-spring-reactor --publish 9991:8080 --network microservices-net --replicas 2 mitosis/microservice-spring-reactor'
                }                 
            """.stripIndent())
        }
    }
}