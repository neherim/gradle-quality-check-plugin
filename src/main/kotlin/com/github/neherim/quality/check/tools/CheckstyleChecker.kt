package com.github.neherim.quality.check.tools

import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin

object CheckstyleChecker {
    fun addPlugin(root: Project, target: Project, ext: CheckstyleQualityExtension) {
        if (ext.enabled) {
            target.plugins.apply(CheckstylePlugin::class.java)
            target.extensions.configure(CheckstyleExtension::class.java) {
                it.toolVersion = ext.toolVersion
                it.configFile = root.file(ext.configFile)
                it.isIgnoreFailures = ext.ignoreFailures
                it.isShowViolations = ext.showViolations
            }

            target.tasks.withType(Checkstyle::class.java) {
                it.exclude(ext.exclude)
                it.maxWarnings = ext.maxWarnings
                it.maxErrors = ext.maxErrors
            }
        }
    }
}