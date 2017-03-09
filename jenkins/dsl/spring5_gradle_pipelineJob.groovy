pipelineJob('spring5-gradle-pipeline') {
    scm {
        git {
            remote {
                github("NirbyApp/mitosis-microservice-spring-reactor")
            }
        }
    }
    definition {
        cpsScm {
            scm {
                github("NirbyApp/mitosis-microservice-spring-reactor", "master")
            }
            scriptPath("Jenkinsfile")
        }
    }
}