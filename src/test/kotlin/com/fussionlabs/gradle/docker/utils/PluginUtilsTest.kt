package com.fussionlabs.gradle.docker.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PluginUtilsTest {

    @Test
    fun testExec() {
        val (exitCode, output) = PluginUtils.exec(listOf("echo", "works"))
        assertEquals(0, exitCode)
        assertEquals("works", output.trim())
    }

    @Test
    fun testExecFailure() {
        val (exitCode, _) = PluginUtils.exec(listOf("docker", "invalidFlag"))
        assertNotEquals(0, exitCode)
    }
}