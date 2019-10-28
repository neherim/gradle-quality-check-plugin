package com.github.neherim.quality.check.tools

import info.solidsoft.gradle.pitest.PitestPlugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

object PitestTestCoverage {

    fun addPlugin(root: Project, target: Project) {
        target.plugins.apply(PitestPlugin::class.java)
        target.extensions.configure(JacocoPluginExtension::class.java) {
            it.toolVersion = ext.toolVersion
        }

        target.tasks.withType(JacocoReport::class.java) {
            it.reports.xml.isEnabled = ext.xmlReportEnabled
            it.reports.html.isEnabled = ext.htmlReportEnabled
            it.reports.csv.isEnabled = ext.csvReportEnabled
        }
    }
}