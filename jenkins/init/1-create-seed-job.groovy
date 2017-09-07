import jenkins.model.*

def jobName = "MitosisGeneratorJob"

def configXml = """\
  <?xml version='1.0' encoding='UTF-8'?>
  <project>
    <actions/>
    <description>Create Jenkins jobs from DSL groovy files</description>
    <keepDependencies>false</keepDependencies>
    <properties>
    </properties>
    <canRoam>true</canRoam>
    <disabled>false</disabled>
    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
    <triggers/>
    <concurrentBuild>false</concurrentBuild>
    <builders>
      <javaposse.jobdsl.plugin.ExecuteDslScripts plugin="job-dsl@1.64">
        <targets>**/*.groovy</targets>
        <usingScriptText>false</usingScriptText>
        <ignoreExisting>false</ignoreExisting>
        <removedJobAction>IGNORE</removedJobAction>
        <removedViewAction>IGNORE</removedViewAction>
        <lookupStrategy>JENKINS_ROOT</lookupStrategy>
        <additionalClasspath></additionalClasspath>
      </javaposse.jobdsl.plugin.ExecuteDslScripts>
    </builders>
    <publishers/>
    <buildWrappers/>
  </project>
""".stripIndent()

def jenkins = Jenkins.getInstance()

if (!Jenkins.instance.getItem(jobName)) {
  def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
  try {
    def seedJob = jenkins.createProjectFromXML(jobName, xmlStream)
    seedJob.scheduleBuild(0, null)
    jenkins.save()
  } catch (ex) {
    println "ERROR: ${ex}"
    println configXml.stripIndent()
  }
}
