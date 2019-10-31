package com.github.neherim.quality.check.tools

import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
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
                it.reports.html.destination = ext.htmlReportsDestination
                it.reports.xml.destination = ext.xmlReportsDestination
                it.reports.csv.destination = ext.csvReportsDestination
                it.executionData.setFrom(target.fileTree(target.buildDir).include("/jacoco/*.exec"))
            }

            if (ext.exclude.isNotEmpty()) {
                target.tasks.withType(JacocoReport::class.java) {
                    val files = root.files(it.classDirectories.files.map { classDir ->
                        root.fileTree(mapOf("dir" to classDir, "exclude" to ext.exclude))
                    })
                    it.classDirectories.setFrom(files)
                }
            }
        }
    }
}