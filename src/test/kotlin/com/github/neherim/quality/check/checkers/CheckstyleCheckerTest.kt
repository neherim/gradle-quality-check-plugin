package com.github.neherim.quality.check.checkers

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class CheckstyleCheckerTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    private val emptyPluginDefinition = "qualityCheck { }"

    private val goodCodeSample = """
                package test;
                
                class Foo {
                    private static final String VARIABLE = "UNUSED";
                }
            """.trimIndent()

    private val badCodeSample = """
                package test;
                
                class Foo {
                    private final static String VARIABLE = "UNUSED";
                }
            """.trimIndent()

    @Test
    fun success() {
        TestProject(testProjectDir)
            .pluginSettings(emptyPluginDefinition)
            .fileFromResource("config/checkstyle/checkstyle.xml", "/checkstyle.xml")
            .file("src/main/java/test/Foo.java", goodCodeSample)
            .successBuild("checkstyleMain")
    }

    @Test
    fun fail() {
        TestProject(testProjectDir)
            .pluginSettings(emptyPluginDefinition)
            .fileFromResource("config/checkstyle/checkstyle.xml", "/checkstyle.xml")
            .file("src/main/java/test/Foo.java", badCodeSample)
            .failBuild("checkstyleMain", "Checkstyle rule violations were found")
            .reportContains(
                "build/reports/checkstyle/main.html",
                "'static' modifier out of order with the JLS suggestions."
            )
    }
}