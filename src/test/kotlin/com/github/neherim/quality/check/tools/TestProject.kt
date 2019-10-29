package com.github.neherim.quality.check.tools

import org.gradle.testkit.runner.GradleRunner
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.rules.TemporaryFolder
import java.io.File

class TestProject(private val directory: TemporaryFolder) {

    fun pluginSettings(settings: String, dependencies: List<String> = listOf()) = apply {
        val dependenciesBlock = if (dependencies.isNotEmpty()) {
            """
            dependencies { 
              ${dependencies.joinToString("\n")} 
            }
            """.trimIndent()
        } else {
            ""
        }
        directory.newFile("build.gradle").writeText(
            """
            plugins {
              id "java"
              id "com.github.neherim.quality.check"
            }
            $settings
            repositories {
              mavenCentral()
            }
            $dependenciesBlock
            """.trimIndent()
        )
    }

    fun file(path: String, content: String) = apply {
        val file = directory.root.resolve(path)
        file.parentFile.mkdirs()
        file.writeText(content)
    }

    fun fileFromResource(path: String, resource: String) = apply {
        val file = directory.root.resolve(path)
        File(this.javaClass.getResource(resource).toURI()).copyTo(file)
    }

    fun successBuild(taskToRun: String) = apply {
        val build = run(taskToRun).build()
        println(build.output)
    }

    fun failBuild(taskToRun: String, containsMessage: String) = apply {
        val buildResult = run(taskToRun).buildAndFail()
        assertThat(buildResult.output, buildResult.output, containsString(containsMessage))
    }

    fun reportContains(reportFile: String, containsMessage: String) = apply {
        val reportFileText = directory.root.resolve(reportFile).readText()
        assertThat(reportFileText, containsString(containsMessage))
    }

    fun reportNotContains(reportFile: String, containsMessage: String) = apply {
        val reportFileText = directory.root.resolve(reportFile).readText()
        assertThat(reportFileText, not(containsString(containsMessage)))
    }

    private fun run(taskToRun: String) =
        GradleRunner.create().withPluginClasspath().withProjectDir(directory.root).withArguments(
            taskToRun,
            "--stacktrace"
        )
}