package com.github.neherim.quality.check.tools

import com.github.spotbugs.SpotBugsExtension
import com.github.spotbugs.SpotBugsPlugin
import com.github.spotbugs.SpotBugsTask
import org.gradle.api.Project

object SpotBugsChecker {
    fun addPlugin(root: Project, target: Project, ext: SpotbugsQualityExtension) {
        if (ext.enabled) {
            target.plugins.apply(SpotBugsPlugin::class.java)
            target.extensions.configure(SpotBugsExtension::class.java) {
                it.isIgnoreFailures = ext.ignoreFailures
                it.toolVersion = ext.toolVersion
                it.effort = ext.effort
                it.reportLevel = ext.reportLevel
                if (root.file(ext.excludeFilter).exists()) {
                    it.excludeFilter = root.file(ext.excludeFilter)
                }
            }

            target.tasks.withType(SpotBugsTask::class.java) {
                it.exclude(ext.exclude)
                it.include(ext.include)
                it.reports.html.isEnabled = ext.reportFormat == "html"
                it.reports.xml.isEnabled = ext.reportFormat == "xml"
                it.reports.text.isEnabled = ext.reportFormat == "text"
                it.reports.emacs.isEnabled = ext.reportFormat == "emacs"
            }
            if (ext.plugins.contains("findsecbugs")) {
                target.dependencies.add("spotbugsPlugins", "com.h3xstream.findsecbugs:findsecbugs-plugin:1.10.0")
            }
        }
    }
}