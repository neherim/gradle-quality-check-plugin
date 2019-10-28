package com.github.neherim.quality.check

import com.github.neherim.quality.check.checkers.QualityCheckstyleExtension
import org.gradle.api.Action

open class QualityCheckExtension {
    var checkstyleExtension = QualityCheckstyleExtension()
    fun checkstyle(action: Action<in QualityCheckstyleExtension>) {
        action.execute(checkstyleExtension)
    }
}