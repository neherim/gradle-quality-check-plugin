package com.github.neherim.quality.check.tools

open class SpotbugsQualityExtension {
    var enabled = true
    var toolVersion = "4.0.1"
    var excludeFilter = "config/spotbugs/spotbugs.xml"
    var ignoreFailures = false
    var effort = "max"
    var reportLevel = "medium"
    var include = listOf("**/*.java")
    var exclude = listOf<String>()
    var reportFormat = "xml"
    var plugins = listOf("findsecbugs")
}
