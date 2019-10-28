package com.github.neherim.quality.check.tools

import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

object JacocoTestCoverage {

    fun addPlugin(root: Project, target: Project, ext: JacocoQualityExtension) {
        if (ext.enabled) {
            target.plugins.apply(JacocoPlugin::class.java)
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
}