package com.github.neherim.quality.check.tools

import java.io.File

open class JacocoQualityExtension {
    var enabled = true
    var exclude = listOf<String>()
    var toolVersion = "0.8.5"
    var xmlReportEnabled = true
    var csvReportEnabled = false
    var htmlReportEnabled = true
    var xmlReportsDestination: File? = null
    var csvReportsDestination: File? = null
    var htmlReportsDestination: File? = null
}