pipelineJob('microservice-spark-pipeline') {
    scm {
        git {
            remote {
                github("NirbyApp/mitosis-microservice-spark-cassandra")
            }
        }
    }
    definition {
        cpsScm {
            scm {
                github("NirbyApp/mitosis-microservice-spark-cassandra", "master")
            }
            scriptPath("Jenkinsfile")
        }
    }
}