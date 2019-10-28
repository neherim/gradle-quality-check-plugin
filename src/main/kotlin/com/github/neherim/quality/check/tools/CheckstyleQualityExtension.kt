package com.github.neherim.quality.check.tools

open class CheckstyleQualityExtension {
    var enabled: Boolean = true
    var toolVersion: String = "8.6"
    var configFile: String = "config/checkstyle/checkstyle.xml"
    var ignoreFailures: Boolean = false
    var showViolations: Boolean = true
    var exclude: List<String> = listOf()
    var maxWarnings = 0
    var maxErrors = 0
}