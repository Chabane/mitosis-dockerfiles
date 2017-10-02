import jenkins.model.*
import hudson.security.*

def env = System.getenv()

def jenkins = Jenkins.getInstance()

// Init gradle - https://stackoverflow.com/a/40504853
def desc = jenkins.getDescriptor("hudson.plugins.gradle.GradleInstallation")
def gradle =  new hudson.plugins.gradle.GradleInstallation("GRADLE_TOOL", env.GRADLE_HOME, null);
desc.setInstallations(gradle)
desc.save()

plugin=Jenkins.instance.getExtensionList(org.jvnet.hudson.plugins.SbtPluginBuilder.DescriptorImpl.class)[0];
tool = plugin.installations.find {
  it.name == "sbt"
}

if (tool == null) {
  println "Registering sbt tool"
  i=(plugin.installations as List);
  i.add(new org.jvnet.hudson.plugins.SbtPluginBuilder.SbtInstallation("sbt", "/usr/share/sbt/bin/sbt-launch.jar", "", []));
  plugin.installations=i
  plugin.save()
} else {
  println "sbt tool has been already registered"
}

jenkins.save()
