pipelineJob('microservice-nodejs-pipeline') {
    scm {
        git {
            remote {
                github("NirbyApp/mitosis-microservice-nodejs-angular")
            }
        }
    }
    definition {
        cpsScm {
            scm {
                github("NirbyApp/mitosis-microservice-nodejs-angular", "master")
            }
            scriptPath("Jenkinsfile")
        }
    }
}
