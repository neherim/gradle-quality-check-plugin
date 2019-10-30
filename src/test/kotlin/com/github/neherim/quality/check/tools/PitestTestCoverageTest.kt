package com.github.neherim.quality.check.tools

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class PitestTestCoverageTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    private val pluginDefinition = """
        group = 'good'
        qualityCheck { }""".trimIndent()

    private val codeSample = """
                package good;
                
                public class GoodClass {
                    public static int sum(int a, int b) {
                        return a + b;
                    }
                    
                    public static int prod(int a, int b) {
                        return a * b;
                    }
                }
            """.trimIndent()

    private val testSample = """
                package good;
                import org.junit.Test;
                import org.junit.Assert;
                
                public class GoodTest {
                    @Test
                    public void testSum() {
                        Assert.assertEquals(GoodClass.sum(1, 2), 3);
                    }
                }
            """.trimIndent()

    @Test
    fun success() {        
        TestProject(testProjectDir)
            .pluginSettings(
                pluginDefinition,
                listOf("testCompile 'junit:junit:4.12'")
            )
            .file("src/main/java/good/GoodClass.java", codeSample)
            .file("src/test/java/good/GoodTest.java", testSample)
            .successBuild("pitest")
            .reportContains("build/reports/pitest/mutations.xml", 
                "<mutation detected='true' status='KILLED' numberOfTestsRun='1'><sourceFile>GoodClass.java</sourceFile>")
    }
}