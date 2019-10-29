package com.github.neherim.quality.check.tools

open class PmdQualityExtension {
    var enabled = true
    var toolVersion = "6.9.0"
    var ruleSetFile = "config/pmd/pmd.xml"
    var ignoreFailures: Boolean = false
    var source = "src"
    var include: List<String> = listOf()
    var exclude: List<String> = listOf()
    var consoleOutput = true
    var xmlReportEnabled = true
    var htmlReportEnabled = true
}
