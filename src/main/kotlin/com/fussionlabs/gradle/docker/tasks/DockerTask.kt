package com.fussionlabs.gradle.docker.tasks

import com.fussionlabs.gradle.docker.DOCKER_BINARY
import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.Input

open class DockerTask: AbstractExecTask<DockerTask>(DockerTask::class.java) {

    @Input
    var dockerTaskArgs: MutableList<String> = mutableListOf()

    override fun exec() {
        executable = DOCKER_BINARY
        args(dockerTaskArgs)

        logger.info("dockerTaskArgs: $dockerTaskArgs")

        super.exec()
    }
}