import jenkins.model.*
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl

def env = System.getenv()

// https://stackoverflow.com/a/37618881
def addPassword = { username, new_password ->
   
        def credentials_store = Jenkins.instance.getExtensionList(
            'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
            )[0].getStore()

        def scope = CredentialsScope.GLOBAL

        def description = ""

        def result = credentials_store.addCredentials(
            com.cloudbees.plugins.credentials.domains.Domain.global(), 
            new UsernamePasswordCredentialsImpl(scope, "MitosisArtifactoryCredentialsId", description, username, new_password)
            )

        if (result) {
            println "credential added for ${username}" 
        } else {
            println "failed to add credential for ${username}"
        }
}

addPassword(env.ARTIFACTORY_USER, env.ARTIFACTORY_PASSWORD)