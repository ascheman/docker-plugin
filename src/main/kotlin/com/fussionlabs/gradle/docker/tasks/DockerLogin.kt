package com.fussionlabs.gradle.docker.tasks

import com.fussionlabs.gradle.docker.utils.PluginUtils.dockerExt
import java.io.ByteArrayInputStream

open class DockerLogin: DockerTask() {

    override fun exec() {
        val username = project.dockerExt.username
        val password = project.dockerExt.password

        dockerTaskArgs.addAll(listOf("login", "--username", username, "--password-stdin"))
        standardInput = ByteArrayInputStream(password.toByteArray())

        super.exec()
    }
}