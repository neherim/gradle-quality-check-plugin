package com.github.neherim.quality.check

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension

open class QualityCheckExtension(
    val project: Project
) {
    var checkstyleExtension = CheckstyleExtension(project)
    fun checkstyleExtension(action: Action<in CheckstyleExtension>) {
        action.execute(checkstyleExtension)
    }
}

class PenaltyExtension(
    var maxWarnings: Int = Int.MAX_VALUE,
    var maxErrors: Int = 0
)