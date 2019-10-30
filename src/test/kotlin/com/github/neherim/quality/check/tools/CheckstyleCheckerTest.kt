package com.github.neherim.quality.check.tools

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class CheckstyleCheckerTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    private val emptyPluginDefinition = "qualityCheck { }"

    private val goodCodeSample = """
                package good;
                
                class GoodClass {
                    private static final String VARIABLE = "UNUSED";
                }
            """.trimIndent()

    private val badCodeSample = """
                package bad;
                
                class BadClass {
                    private final static String VARIABLE = "UNUSED";
                }
            """.trimIndent()

    @Test
    fun success() {
        TestProject(testProjectDir)
            .pluginSettings(emptyPluginDefinition)
            .file("src/main/java/good/GoodClass.java", goodCodeSample)
            .successBuild("checkstyleMain")
    }

    @Test
    fun fail() {
        TestProject(testProjectDir)
            .pluginSettings(emptyPluginDefinition)
            .file("src/main/java/bad/BadClass.java", badCodeSample)
            .failBuild("checkstyleMain", "Checkstyle rule violations were found")
            .reportContains(
                "build/reports/checkstyle/main.html",
                "'static' modifier out of order with the JLS suggestions."
            )
    }

    @Test
    fun excludeSources() {
        TestProject(testProjectDir)
            .pluginSettings("""
                qualityCheck {
                  checkstyle {
                    exclude = ['**/bad/**']
                  }
                }
            """.trimIndent())
            .file("src/main/java/good/GoodClass.java", goodCodeSample)
            .file("src/main/java/bad/BadClass.java", badCodeSample)
            .successBuild("checkstyleMain")
    }
}