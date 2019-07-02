# Gradle boilerplate
[![Build Status](https://travis-ci.org/ghostbuster91/gradle-boilerplate.svg?branch=master)](https://travis-ci.org/ghostbuster91/gradle-boilerplate)

This plugin is just an adaptation of amazing [sbt-boilerplate plugin](https://github.com/sbt/sbt-boilerplate) for gradle.

How to:

```gradle
sourceSets {
    generated{
        java.srcDir "${buildDir}/generated/src/java/"
        kotlin.srcDir "${buildDir}/generated/src/java/"
        compileClasspath = sourceSets.main.compileClasspath
    }
    main {
        compileClasspath += sourceSets.generated.output
    }
}

compileGeneratedJava.dependsOn(generateBoilerplate)
compileGeneratedKotlin.dependsOn(generateBoilerplate)

compileJava {
    dependsOn(compileGeneratedJava)
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
    dependsOn(compileGeneratedKotlin)
}
```

Full example which shows how to generate rxJava combineLatest methods for more than 9 parameters can be found [here](https://github.com/ghostbuster91/gradle-boilerplate-example)


### Releasing
1. Update version in build.gradle
2. Create tag and push to github, travis will handle the rest
