package com.github.neherim.quality.check.checkers

import org.gradle.api.Project
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdExtension
import org.gradle.api.plugins.quality.PmdPlugin

object PmdChecker {
    fun addChecker(root: Project, target: Project, ext: PmdQualityExtension) {
        if (ext.enabled) {
            target.plugins.apply(PmdPlugin::class.java)
            target.extensions.configure(PmdExtension::class.java) {
                it.toolVersion = ext.toolVersion
                it.isIgnoreFailures = ext.ignoreFailures
                it.isConsoleOutput = ext.consoleOutput

                if (root.file(ext.ruleSetFile).exists()) {
                    it.ruleSetFiles = root.files(root.file(ext.ruleSetFile))
                }
            }

            target.tasks.withType(Pmd::class.java) {
                it.exclude(ext.exclude)
                it.include(ext.include)
            }
        }
    }
}