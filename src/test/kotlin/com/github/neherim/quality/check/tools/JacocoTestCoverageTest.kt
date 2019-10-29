package com.github.neherim.quality.check.tools

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class JacocoTestCoverageTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    private val pluginDefinition = """
        qualityCheck { 
          jacoco {
            csvReportEnabled = true
            exclude = ['**/generated/**']
          }
        }""".trimIndent()

    private val codeSample = """
                package good;
                
                public class GoodClass {
                    public static int sum(int a, int b) {
                        return a + b;
                    }
                }
            """.trimIndent()

    private val excludedClass = """
                package generated;
                
                public class GeneratedClass {
                    public static int minus(int a, int b) {
                        return a - b;
                    }
                }
            """.trimIndent()

    private val testSample = """
                package good;
                import org.junit.Test;
                
                public class GoodTest {
                    @Test
                    public void testSum() {
                        GoodClass.sum(1, 2);
                    }
                }
            """.trimIndent()

    @Test
    fun success() {
        TestProject(testProjectDir)
            .pluginSettings(pluginDefinition, 
                listOf("testCompile 'junit:junit:4.12'"))
            .file("src/main/java/good/GoodClass.java", codeSample)
            .file("src/test/java/good/GoodTest.java", testSample)
            .successBuild("test")
            .successBuild("jacocoTestReport")
            .reportContains("build/reports/jacoco/test/jacocoTestReport.csv", "GoodClass,3,4,0,0,1,1,1,1,1,1")
    }

    @Test
    fun exclude() {
        TestProject(testProjectDir)
            .pluginSettings(pluginDefinition,
                listOf("testCompile 'junit:junit:4.12'"))
            .file("src/main/java/good/GoodClass.java", codeSample)
            .file("src/main/java/generated/GeneratedClass.java", excludedClass)
            .file("src/test/java/good/GoodTest.java", testSample)
            .successBuild("test")
            .successBuild("jacocoTestReport")
            .reportContains("build/reports/jacoco/test/jacocoTestReport.csv", "GoodClass,3,4,0,0,1,1,1,1,1,1")
            .reportNotContains("build/reports/jacoco/test/jacocoTestReport.csv", "GeneratedClass")
    }
}