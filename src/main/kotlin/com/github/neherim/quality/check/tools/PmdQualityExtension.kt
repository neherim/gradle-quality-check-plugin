package com.github.neherim.quality.check.tools

open class PmdQualityExtension {
    var enabled = true
    var toolVersion = "6.9.0"
    var ruleSetFile = "config/pmd/pmd.xml"
    var ignoreFailures: Boolean = false
    var source = "src"
    var include: List<String> = listOf("**/*.java")
    var exclude: List<String> = listOf()
    var consoleOutput = false
    var xmlReport = true
    var htmlReport = true
}
