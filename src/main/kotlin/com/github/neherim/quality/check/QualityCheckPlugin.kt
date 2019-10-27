package com.github.neherim.quality.check

import com.github.neherim.quality.check.checkers.CheckstyleChecker
import org.gradle.api.Plugin
import org.gradle.api.Project

class QualityCheckPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("qualityCheck", QualityCheckExtension::class.java, project)

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
        CheckstyleChecker.addCheckstyle(rootProject, extension.checkstyleExtension)
    }
}