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
                    
                    public static int prod(int a, int b) {
                        return a * b;
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

    private val integrationTestSample = """
                package good;
                import org.junit.Test;
                
                public class GoodIntegrationTest {
                    @Test
                    public void testProd() {
                        GoodClass.prod(1, 2);
                    }
                }
            """.trimIndent()

    private val multisourcePluginDefinition = """
        qualityCheck { 
          jacoco {
            csvReportEnabled = true
            exclude = ['**/generated/**']
          }
        }
        
        sourceSets {
          integrationTest {
            compileClasspath += main.output + test.output
            runtimeClasspath += main.output + test.output
          }
        }
        
        configurations {
          integrationTestCompile.extendsFrom testCompile
          integrationTestRuntime.extendsFrom testRuntime
        }
        
        task integrationTest(type: Test, dependsOn: 'integrationTestClasses') {
          testClassesDirs = sourceSets.integrationTest.output.classesDirs
          classpath = sourceSets.integrationTest.runtimeClasspath
          jvmArgs += ["-Duser.timezone=Europe/Moscow"]
        }
        """.trimIndent()

    @Test
    fun multisourceTotalReportSuccess() {
        TestProject(testProjectDir)
            .pluginSettings(
                multisourcePluginDefinition,
                listOf("testCompile 'junit:junit:4.12'")
            )
            .file("src/main/java/good/GoodClass.java", codeSample)
            .file("src/test/java/good/GoodTest.java", testSample)
            .file("src/integrationTest/java/good/GoodIntegrationTest.java", integrationTestSample)
            .successBuild("test")
            .successBuild("integrationTest")
            .successBuild("jacocoTestReport")
            .reportContains("build/reports/jacoco/test/jacocoTestReport.csv", "GoodClass,3,8,0,0,1,2,1,2,1,2")
    }

    @Test
    fun success() {
        TestProject(testProjectDir)
            .pluginSettings(
                pluginDefinition,
                listOf("testCompile 'junit:junit:4.12'")
            )
            .file("src/main/java/good/GoodClass.java", codeSample)
            .file("src/test/java/good/GoodTest.java", testSample)
            .successBuild("test")
            .successBuild("jacocoTestReport")
            .reportContains("build/reports/jacoco/test/jacocoTestReport.csv", "GoodClass,7,4,0,0,2,1,2,1,2,1")
    }

    @Test
    fun exclude() {
        TestProject(testProjectDir)
            .pluginSettings(
                pluginDefinition,
                listOf("testCompile 'junit:junit:4.12'")
            )
            .file("src/main/java/good/GoodClass.java", codeSample)
            .file("src/main/java/generated/GeneratedClass.java", excludedClass)
            .file("src/test/java/good/GoodTest.java", testSample)
            .successBuild("test")
            .successBuild("jacocoTestReport")
            .reportContains("build/reports/jacoco/test/jacocoTestReport.csv", "GoodClass,7,4,0,0,2,1,2,1,2,1")
            .reportNotContains("build/reports/jacoco/test/jacocoTestReport.csv", "GeneratedClass")
    }
}