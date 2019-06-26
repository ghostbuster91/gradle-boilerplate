package spray.boilerplate

import java.io.{File, FilenameFilter, PrintWriter}

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

import scala.collection.JavaConverters._
import scala.io.Source

object SbtBoilerplateRunner {
  def run(project: Project): Unit = {
    val outputDir = s"${project.getBuildDir.getPath}/generated/src/java"
    new File(outputDir).mkdirs()
    val bradleDir = new File(resourcesDir(project), "bradle")
    if (bradleDir.exists()) {
      val resources = bradleDir.listFiles(new FilenameFilter {
        override def accept(file: File, s: String): Boolean = s.matches(".*bradle$")
      }).toList
      resources.foreach { f =>
        processFile(outputDir, f)
      }
    }
  }

  def resourcesDir(project: Project): File = {
    val sourceSets = project.getProperties.get("sourceSets").asInstanceOf[SourceSetContainer]
    sourceSets.getByName("main").getResources.getSrcDirs.asScala.toList.head
  }

  private def processFile(outputDir: String, f: File): Unit = {
    val bufferedSource = Source.fromFile(f)
    val template = bufferedSource.getLines.mkString("\n")
    val content = Generator.generateFromTemplate(template, 22)
    val outputFile = new File(outputDir, f.getName.replace(".bradle", ""))
    new PrintWriter(outputFile) {
      write(content)
      close()
    }
    bufferedSource.close()
  }
}
