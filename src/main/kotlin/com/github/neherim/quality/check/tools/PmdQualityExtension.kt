package com.github.neherim.quality.check.tools

open class PmdQualityExtension {
    var enabled = true
    var toolVersion = "6.19.0"
    var ruleSetFile = "config/pmd/pmd.xml"
    var ignoreFailures = false
    var source = "src"
    var include = listOf("**/*.java")
    var exclude = listOf<String>()
    var consoleOutput = false
    var xmlReport = true
    var htmlReport = true
}
