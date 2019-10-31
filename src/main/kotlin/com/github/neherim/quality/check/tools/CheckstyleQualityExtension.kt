package com.github.neherim.quality.check.tools

open class CheckstyleQualityExtension {
    var enabled = true
    var toolVersion = "8.6"
    var configFile = "config/checkstyle/checkstyle.xml"
    var ignoreFailures = false
    var showViolations = false
    var include: List<String> = listOf("**/*.java")
    var exclude: List<String> = listOf()
    var maxWarnings = 0
    var maxErrors = 0
    var htmlReport = true
    var xmlReport = true
    var reportsDestination: String? = null
}