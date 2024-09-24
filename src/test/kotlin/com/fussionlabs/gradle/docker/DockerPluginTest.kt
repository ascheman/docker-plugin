package com.fussionlabs.gradle.docker

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class DockerPluginTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun testDefaultTasksExist() {
        val project: Project = ProjectBuilder.builder().withProjectDir(tempDir).build()
        project.pluginManager.apply(DockerPlugin::class.java)

        val extension: DockerPluginExtension = project.extensions.findByType(DockerPluginExtension::class.java)!!
        extension.repository = "dm0275/my-repo"

        // Force project evaluation
        project.getTasksByName("dockerLogin", false)

        val expectedTasks = listOf("dockerLogin", "dockerBuild", "dockerPush")
        expectedTasks.forEach { expectedTask ->
            assertNotNull(project.tasks.getByName(expectedTask))
        }
    }
}