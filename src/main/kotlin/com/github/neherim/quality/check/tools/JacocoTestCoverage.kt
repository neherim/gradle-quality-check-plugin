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
                it.executionData.setFrom(target.fileTree(target.buildDir).include("/jacoco/*.exec"))
                if (!ext.reportsDestination.isNullOrBlank()) {
                    it.reports.html.destination = target.file(ext.reportsDestination)
                    it.reports.xml.destination = target.file(ext.reportsDestination)
                    it.reports.csv.destination = target.file(ext.reportsDestination)
                }
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