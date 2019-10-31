package com.github.neherim.quality.check.tools

open class JacocoQualityExtension {
    var enabled = true
    var exclude = listOf<String>()
    var toolVersion = "0.8.4"
    var xmlReportEnabled = true
    var csvReportEnabled = false
    var htmlReportEnabled = true
    var reportsDestination: String? = null
}