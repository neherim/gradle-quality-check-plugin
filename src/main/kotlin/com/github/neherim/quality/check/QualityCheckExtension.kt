package com.github.neherim.quality.check

import com.github.neherim.quality.check.tools.CheckstyleQualityExtension
import com.github.neherim.quality.check.tools.JacocoQualityExtension
import com.github.neherim.quality.check.tools.PmdQualityExtension
import com.github.neherim.quality.check.tools.SpotbugsQualityExtension
import org.gradle.api.Action

open class QualityCheckExtension {
    var checkstyleExtension = CheckstyleQualityExtension()
    fun checkstyle(action: Action<in CheckstyleQualityExtension>) = action.execute(checkstyleExtension)

    var pmdExtension = PmdQualityExtension()
    fun pmd(action: Action<in PmdQualityExtension>) = action.execute(pmdExtension)

    var spotbugsExtension = SpotbugsQualityExtension()
    fun spotbugs(action: Action<in SpotbugsQualityExtension>) = action.execute(spotbugsExtension)
    
    var jacocoExtension = JacocoQualityExtension()
    fun jacoco(action: Action<in JacocoQualityExtension>) = action.execute(jacocoExtension)
}
