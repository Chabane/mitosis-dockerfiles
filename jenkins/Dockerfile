FROM jenkins:2.60.2
MAINTAINER Nirby

USER root
# Install Docker client
ENV DOCKER_BUCKET get.docker.com
ENV DOCKER_VERSION 1.13.0
ENV DOCKER_SHA256 fc194bb95640b1396283e5b23b5ff9d1b69a5e418b5b3d774f303a7642162ad6

RUN set -x \
	&& curl -fSL "https://${DOCKER_BUCKET}/builds/Linux/x86_64/docker-$DOCKER_VERSION.tgz" -o docker.tgz \
	&& echo "${DOCKER_SHA256} *docker.tgz" | sha256sum -c - \
	&& tar -xzvf docker.tgz \
	&& mv docker/* /usr/local/bin/ \
	&& rmdir docker \
	&& rm docker.tgz \
	&& docker -v

# Install default plugins
RUN /usr/local/bin/install-plugins.sh ansible:0.6.2 artifactory:2.12.2 sonar:2.6.1 git:3.5.1 subversion:2.9 matrix-auth:1.7 workflow-aggregator:2.5 docker-workflow:1.12 docker-commons:1.8 blueocean:1.2.0 job-dsl:1.64 pipeline-maven:3.0.0 swarm:3.4 kubernetes:0.12 nodejs:1.2.3

ENV JENKINS_USER admin
ENV JENKINS_PASS admin

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Copy default user
COPY init/default-user.groovy /usr/share/jenkins/ref/init.groovy.d/
# The place where to put the DSL files for the Seed Job to run
RUN mkdir -p /usr/share/jenkins/ref/jobs/MitosisGeneratorJob/workspace/
# Groovy script to create the "MitosisGeneratorJob" (the standard way, not with DSL)
COPY build/create-seed-job.groovy /usr/share/jenkins/ref/init.groovy.d/
# COPY Seed Job DSL script
COPY dsl/*.groovy /usr/share/jenkins/ref/jobs/MitosisGeneratorJob/workspace/
# COPY config
# COPY config/*.xml /usr/share/jenkins/ref/

