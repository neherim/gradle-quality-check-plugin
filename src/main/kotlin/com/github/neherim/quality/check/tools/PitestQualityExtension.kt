package com.github.neherim.quality.check.tools

open class PitestQualityExtension {
    var enabled = true
    var toolVersion: String = "1.4.9"
    var testPlugin = "junit"
    var threads = 4
    var outputFormats = listOf("XML", "HTML")
    var targetClasses = setOf<String>()
    var timestampedReports = false
}