# Gradle Quality Check Plugin
[![Build Status](https://travis-ci.com/neherim/gradle-quality-check-plugin.svg?branch=master)](https://travis-ci.com/neherim/gradle-quality-check-plugin)

### Supported tools

 * [`Checkstyle`](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
 * [`PMD`](https://docs.gradle.org/current/userguide/pmd_plugin.html)
 * [`SpotBugs`](https://plugins.gradle.org/plugin/com.github.spotbugs)
 * [`JaCoCo`](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
 * [`Pitest`](https://plugins.gradle.org/plugin/info.solidsoft.pitest)
 
### Add the plugin to your project
Using the plugins DSL:
```groovy
plugins {
  id "com.github.neherim.quality.check" version "0.1.4"
}
```

Using legacy plugin application:
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.neherim:gradle-quality-check-plugin:0.1.4"
  }
}

apply plugin: "com.github.neherim.quality.check"
```

### Configuration
```groovy
qualityCheck {
  checkstyle {
    enabled = true
    toolVersion = "8.6"
    configFile = "config/checkstyle/checkstyle.xml"
    ignoreFailures = false
    showViolations = false
    include = ["**/*.java"]
    exclude = []
    maxWarnings = 0
    maxErrors = 0
    htmlReport = true
    xmlReport = true
    reportsDestination = ""
  }
  pmd {
    enabled = true
    toolVersion = "6.9.0"
    ruleSetFile = "config/pmd/pmd.xml"
    ignoreFailures = false
    source = "src"
    include = ["**/*.java"]
    exclude = []
    consoleOutput = false
    xmlReport = true
    htmlReport = true
  }
  spotbugs {
    enabled = true
    toolVersion = "3.1.12"
    excludeFilter = "config/spotbugs/spotbugs.xml"
    ignoreFailures = false
    effort = "max"
    reportLevel = "medium"
    include = ["**/*.java"]
    exclude = []
    reportFormat = "html" // html | xml | text | emacs
    plugins = ["findsecbugs"]
  }
  jacoco {
    enabled = true
    exclude = []
    toolVersion = "0.8.4"
    xmlReportEnabled = true
    csvReportEnabled = false
    htmlReportEnabled = true
    reportsDestination = ""
  }
  pitest {
    enabled = true
    toolVersion = "1.4.9"
    testPlugin = "junit" // junit | junit5 
    threads = 4
    outputFormats = ["XML", "HTML"]
    targetClasses = []
    excludedClasses = []
    timestampedReports = false
  }
}
```
