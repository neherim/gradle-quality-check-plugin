package com.github.neherim.quality.check.tools

open class PitestQualityExtension {
    var enabled = true
    var toolVersion = "1.5.0"
    var testPlugin = "junit"
    var threads = 4
    var outputFormats = listOf("XML", "HTML")
    var targetClasses = setOf<String>()
    var timestampedReports = false
    var excludedClasses = setOf<String>()
    var useClasspathFile = false
}