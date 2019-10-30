package com.github.neherim.quality.check

import com.github.neherim.quality.check.tools.*
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

    var pitestExtension = PitestQualityExtension()
    fun pitest(action: Action<in PitestQualityExtension>) = action.execute(pitestExtension)
}
