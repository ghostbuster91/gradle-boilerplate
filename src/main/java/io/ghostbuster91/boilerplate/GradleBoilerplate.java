package io.ghostbuster91.boilerplate;

import org.gradle.api.*;
import spray.boilerplate.SbtBoilerplateRunner;

import java.io.File;

@SuppressWarnings("unused")
public class GradleBoilerplate implements Plugin<Project> {
    public void apply(final Project project) {
        project.getTasks().create("generateBoilerplate", DefaultTask.class, defaultTask -> {
//                defaultTask.getInputs().dir(SbtBoilerplateRunner.resourcesDir(project)); //TODO: fix
            defaultTask.getOutputs().dir(new File(new File(new File(project.getBuildDir(), "generated"), "src"), "java"));
            defaultTask.doLast(task -> SbtBoilerplateRunner.run(project));
        });
    }
}
