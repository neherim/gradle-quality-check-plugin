package com.github.neherim.quality.check

import com.github.neherim.quality.check.tools.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class QualityCheckPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("qualityCheck", QualityCheckExtension::class.java)

        if (project.subprojects.size > 0) {
            project.subprojects { subProject ->
                subProject.afterEvaluate {
                    addCodeQualityTools(it, project, extension)
                }
            }
        } else {
            project.afterEvaluate {
                addCodeQualityTools(it, project, extension)
            }
        }
    }

    private fun addCodeQualityTools(project: Project, rootProject: Project, extension: QualityCheckExtension) {
        CheckstyleChecker.addPlugin(rootProject, project, extension.checkstyleExtension)
        PmdChecker.addPlugin(rootProject, project, extension.pmdExtension)
        SpotBugsChecker.addPlugin(rootProject, project, extension.spotbugsExtension)
        JacocoTestCoverage.addPlugin(rootProject, project, extension.jacocoExtension)
        PitestTestCoverage.addPlugin(rootProject, project, extension.pitestExtension)
    }
}