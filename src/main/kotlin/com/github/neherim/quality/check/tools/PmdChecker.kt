package com.github.neherim.quality.check.tools

import org.gradle.api.Project
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdExtension
import org.gradle.api.plugins.quality.PmdPlugin
import java.io.File

object PmdChecker {
    fun addPlugin(root: Project, target: Project, ext: PmdQualityExtension) {
        if (ext.enabled) {
            target.plugins.apply(PmdPlugin::class.java)
            val ruleSetFile = root.file(ext.ruleSetFile)
            if (!ruleSetFile.exists()) {
                createDefaultRuleSet(ruleSetFile)
            }
            target.extensions.configure(PmdExtension::class.java) {
                it.toolVersion = ext.toolVersion
                it.isIgnoreFailures = ext.ignoreFailures
                it.isConsoleOutput = ext.consoleOutput
                it.ruleSetFiles = root.files(ruleSetFile)

            }

            target.tasks.withType(Pmd::class.java) {
                it.source = target.fileTree(ext.source)
                it.exclude(ext.exclude)
                it.include(ext.include)
                it.reports.html.isEnabled = ext.htmlReportEnabled
                it.reports.xml.isEnabled = ext.xmlReportEnabled
            }
        }
    }

    private fun createDefaultRuleSet(file: File) {
        file.parentFile.mkdirs()
        File(this.javaClass.getResource("/pmd-default-ruleset.xml").toURI()).copyTo(file, false)
    }
}