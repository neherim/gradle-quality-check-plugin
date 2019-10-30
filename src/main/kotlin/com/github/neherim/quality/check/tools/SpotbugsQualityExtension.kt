package com.github.neherim.quality.check.tools

open class SpotbugsQualityExtension {
    var enabled: Boolean = true
    var toolVersion: String = "3.1.12"
    var excludeFilter: String = "config/spotbugs/spotbugs.xml"
    var ignoreFailures: Boolean = false
    var effort: String = "max"
    var reportLevel: String = "medium"
    var include: List<String> = listOf("**/*.java")
    var exclude: List<String> = listOf()
    var reportFormat: String = "html"
}
