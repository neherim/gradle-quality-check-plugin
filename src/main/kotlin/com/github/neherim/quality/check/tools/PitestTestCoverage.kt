package com.github.neherim.quality.check.tools

import info.solidsoft.gradle.pitest.PitestPlugin
import info.solidsoft.gradle.pitest.PitestPluginExtension
import org.gradle.api.Project


object PitestTestCoverage {

    fun addPlugin(root: Project, target: Project, ext: PitestQualityExtension) {
        if (ext.enabled) {
            target.buildscript.configurations.maybeCreate("pitest")
            target.buildscript.dependencies.add("pitest", "org.pitest:pitest-junit5-plugin:0.9")
            
            target.plugins.apply(PitestPlugin::class.java)
            target.extensions.configure(PitestPluginExtension::class.java) {
                it.pitestVersion.set(ext.toolVersion)
                it.testPlugin.set(ext.testPlugin)
                it.threads.set(ext.threads)
                it.timestampedReports.set(ext.timestampedReports)
                it.outputFormats.empty()
                it.outputFormats.addAll(ext.outputFormats)
                it.excludedClasses.set(ext.excludedClasses)
                if (ext.targetClasses.isNotEmpty()) {
                    it.targetClasses.set(ext.targetClasses)
                }
            }
        }
    }
}