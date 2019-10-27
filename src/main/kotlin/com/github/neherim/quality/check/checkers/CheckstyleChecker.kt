package com.github.neherim.quality.check.checkers

import org.gradle.api.Project
import org.gradle.api.internal.resources.CharSourceBackedTextResource
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.internal.impldep.com.google.common.io.CharSource
import java.io.File

object CheckstyleChecker {
    fun addCheckstyle(prj: Project, extension: CheckstyleExtension) {
        prj.plugins.apply(CheckstylePlugin::class.java)
        val conf = File(
            CheckstyleChecker.javaClass
                .getResource("/google_checks.xml").file
        )

        prj.extensions.configure(CheckstyleExtension::class.java) {
            it.config = CharSourceBackedTextResource("checkstyle.xml", CharSource.wrap(conf.readText()))
        }
    }
}