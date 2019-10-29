package com.github.neherim.quality.check.tools

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class PmdCheckerTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    private val emptyPluginDefinition = "qualityCheck { }"

    private val goodCodeSample = """
                package good;
                /**
                  Comment
                 **/
                public final class GoodClassUtils {
                    /**
                     Comment
                     **/
                    public static final String VARIABLE = "UNUSED";
                    private GoodClassUtils() {
                    }
                }
            """.trimIndent()
    
    @Test
    fun success() {
        TestProject(testProjectDir)
            .pluginSettings(emptyPluginDefinition)
            .file("src/main/java/good/GoodClassUtils.java", goodCodeSample)
            .successBuild("pmdMain")
    }
}