# Gradle boilerplate

This plugin is just an adaptation of amazing [sbt-boilerplate plugin](https://github.com/sbt/sbt-boilerplate) for gradle.

How to:

```gradle
sourceSets {
    generated{
        java.srcDir "${buildDir}/generated/src/java/"
        kotlin.srcDir "${buildDir}/generated/src/java/"
    }
}

compileJava {
    dependsOn(generateBoilerplate)
    source    += sourceSets.generated.java
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
    dependsOn(generateBoilerplate)
    source    += sourceSets.generated.kotlin
}
```
