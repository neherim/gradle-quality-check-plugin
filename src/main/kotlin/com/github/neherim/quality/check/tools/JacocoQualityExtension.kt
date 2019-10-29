package com.github.neherim.quality.check.tools

open class JacocoQualityExtension {
    var enabled = true
    var exclude: List<String> = listOf()
    var toolVersion: String = "0.8.4"
    var xmlReportEnabled = true
    var csvReportEnabled = true
    var htmlReportEnabled = true
}