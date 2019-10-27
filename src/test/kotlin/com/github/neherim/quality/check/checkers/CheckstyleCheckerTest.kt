package com.github.neherim.quality.check.checkers

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CheckstyleCheckerTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    @Test
    fun fails() {
        Roboter(testProjectDir)
            .withJavaFile(
                "src/main/java/test/Foo.java", """
        |package test;
        |class Foo {
        |    private static final String UNUSED_VARIABLE  = "UNUSED";
        |}""".trimMargin()
            )
            .fails(containsMessage = "BugInstance type=\"URF_UNREAD_FIELD\"")
    }

    class Roboter(
        private val directory: TemporaryFolder
    ) {
        init {
            directory.newFile("build.gradle").writeText(
                """
          |plugins {
          |  id "java"
          |  id "com.github.neherim.quality.check"
          |}
          |
          |qualityCheck {
          |  
          |}
          |
          |repositories {
          |  jcenter()
          |}
          |""".trimMargin()
            )
        }

        fun withJavaFile(path: String, content: String) = write(path, content)

        private fun write(path: String, content: String) = apply {
            val paths = path.split("/").dropLast(1).toTypedArray()
            if (paths.isNotEmpty()) directory.newFolder(*paths)
            directory.newFile(path).writeText(content)
        }

        fun succeeds(taskToRun: String = "spotbugs") = apply {
            val buildResult = run(taskToRun).buildAndFail()
            //assertThat(buildResult.task(":$taskToRun")?.outcome).isEqualTo(TaskOutcome.SUCCESS)
        }

        fun fails(taskToRun: String = "checkstyleMain", taskToCheck: String = taskToRun, containsMessage: String) = apply {
            val buildResult = run(taskToRun).buildAndFail()
            val reportXmlFile = File(directory.root, "build/reports/spotbugs/main.xml")

            /*assertThat(buildResult.task(":$taskToCheck")?.outcome).isEqualTo(TaskOutcome.FAILED)
            assertThat(reportXmlFile.exists())
            assertThat(reportXmlFile.readText().contains(containsMessage))*/
        }

        private fun run(taskToRun: String) =
            GradleRunner.create().withPluginClasspath().withProjectDir(directory.root).withArguments(
                taskToRun,
                "--stacktrace"
            )
    }
}