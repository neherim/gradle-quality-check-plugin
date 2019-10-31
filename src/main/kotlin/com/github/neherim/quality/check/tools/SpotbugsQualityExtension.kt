package com.github.neherim.quality.check.tools

open class SpotbugsQualityExtension {
    var enabled = true
    var toolVersion = "3.1.12"
    var excludeFilter = "config/spotbugs/spotbugs.xml"
    var ignoreFailures = false
    var effort = "max"
    var reportLevel = "medium"
    var include = listOf("**/*.java")
    var exclude = listOf<String>()
    var reportFormat = "html"
    var plugins = listOf("findsecbugs")
}
