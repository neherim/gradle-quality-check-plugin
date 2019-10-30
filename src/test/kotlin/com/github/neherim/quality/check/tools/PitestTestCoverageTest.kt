package com.github.neherim.quality.check.tools

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class PitestTestCoverageTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    private val pluginDefinition = """
        group = 'good'
        qualityCheck { }
        """.trimIndent()

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

    private val testSampleJunit5 = """
                package good;
                import org.junit.jupiter.api.Test;
                import static org.junit.jupiter.api.Assertions.assertEquals;
                
                public class GoodTest {
                    @Test
                    public void testSum() {
                        assertEquals(GoodClass.sum(1, 2), 3);
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

    @Test
    fun successJunit5() {
        TestProject(testProjectDir)
            .pluginSettings(
                """
                 group = 'good'
                 qualityCheck {
                   pitest {
                      testPlugin = 'junit5'
                   }
                 }
                 
                 tasks.withType(Test) {
                   useJUnitPlatform()
                 }
                """.trimIndent()
                ,
                listOf(
                    "testCompile 'org.junit.jupiter:junit-jupiter-api:5.5.1'",
                    "testRuntime 'org.junit.jupiter:junit-jupiter-engine:5.5.1'",
                    "testRuntime 'org.junit.vintage:junit-vintage-engine:5.5.1'"
                )
            )
            .file("src/main/java/good/GoodClass.java", codeSample)
            .file("src/test/java/good/GoodTest.java", testSampleJunit5)
            .successBuild("pitest")
            .reportContains(
                "build/reports/pitest/mutations.xml",
                "<mutation detected='true' status='KILLED' numberOfTestsRun='1'><sourceFile>GoodClass.java</sourceFile>"
            )
    }
}