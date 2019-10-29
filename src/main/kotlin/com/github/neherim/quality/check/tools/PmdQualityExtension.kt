package com.github.neherim.quality.check.tools

open class PmdQualityExtension {
    var enabled: Boolean = true
    var toolVersion: String = "6.0.0"
    var ruleSetFile: String = "config/pmd/pmd.xml"
    var ignoreFailures: Boolean = false
    var include: List<String> = listOf("**/*.java")
    var exclude: List<String> = listOf()
    var consoleOutput = true
}
