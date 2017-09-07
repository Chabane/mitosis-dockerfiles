import jenkins.model.*
import hudson.security.*

def env = System.getenv()

def jenkins = Jenkins.getInstance()

// Init gradle - https://stackoverflow.com/a/40504853
def desc = jenkins.getDescriptor("hudson.plugins.gradle.GradleInstallation")
def gradle =  new hudson.plugins.gradle.GradleInstallation("GRADLE_TOOL", env.GRADLE_HOME, null);
desc.setInstallations(gradle)
desc.save()

jenkins.save()
