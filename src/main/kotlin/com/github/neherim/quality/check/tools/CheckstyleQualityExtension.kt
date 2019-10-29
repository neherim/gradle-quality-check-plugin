package com.github.neherim.quality.check.tools

open class CheckstyleQualityExtension {
    var enabled: Boolean = true
    var toolVersion: String = "8.6"
    var configFile: String = "config/checkstyle/checkstyle.xml"
    var ignoreFailures: Boolean = false
    var showViolations: Boolean = false
    var exclude: List<String> = listOf()
    var maxWarnings = 0
    var maxErrors = 0
    var htmlReport = true
    var xmlReport = true
}