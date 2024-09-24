package com.fussionlabs.gradle.docker

import com.fussionlabs.gradle.docker.tasks.DockerBuildx
import com.fussionlabs.gradle.docker.tasks.DockerLogin
import com.fussionlabs.gradle.docker.utils.PluginUtils.dockerExt
import com.fussionlabs.gradle.docker.utils.PluginUtils.logger
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        // Create plugin extension
        project.extensions.create(DOCKER_PLUGIN_EXTENSION, DockerPluginExtension::class.java)

        // Apply the base plugin
        project.plugins.apply("base")

        // Create the login task
        project.tasks.register(DOCKER_LOGIN_TASK, DockerLogin::class.java) { task ->
            task.group = DOCKER_PLUGIN_GROUP
            task.description = "Login to the docker registry"
        }

        // Create the build task
        project.tasks.register(DOCKER_BUILD_TASK, DockerBuildx::class.java) { task ->
            task.group = DOCKER_PLUGIN_GROUP
            task.description = "Build the Docker images"
        }

        // Create the push task
        project.tasks.register(DOCKER_PUSH_TASK, DockerBuildx::class.java) { task ->
            task.dependsOn("dockerBuild")
            task.pushImage = true
            task.group = DOCKER_PLUGIN_GROUP
            task.description = "Push the Docker images"
        }

        project.afterEvaluate {
            validateConfig(project)
        }
    }

    fun validateConfig(project: Project) {
        val extension = project.dockerExt

        if (extension.repository.isEmpty()) {
            throw GradleException("No Docker repository configured. This is a required field.")
        }

        if (extension.dockerFilePath.isEmpty()) {
            logger.warn("No Dockerfile path set, defaulting to project directory: ${project.rootDir.absolutePath}")
            extension.dockerFilePath = project.rootDir.absolutePath
        }
    }
}