package spray.boilerplate

import java.io.{File, PrintWriter}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file._

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
      Files.walkFileTree(bradleDir.toPath, new GeneratingFilesVisitor(bradleDir.toPath, Paths.get(outputDir)))
    }
  }

  def resourcesDir(project: Project): File = {
    val sourceSets = project.getProperties.get("sourceSets").asInstanceOf[SourceSetContainer]
    sourceSets.getByName("main").getResources.getSrcDirs.asScala.toList.head
  }
}

class GeneratingFilesVisitor(val bradleDir: Path, val outputDir: Path) extends SimpleFileVisitor[Path] {
  override def visitFile(file: Path, basicFileAttributes: BasicFileAttributes): FileVisitResult = {
    if (file.toFile.getName.endsWith(".bradle")) {
      val relativePath = bradleDir.relativize(file)
      val outputPath = outputDir.resolve(relativePath)
      val outputTargetDir = new File(outputPath.toFile.getParent)
      outputTargetDir.mkdirs()
      processFile(outputTargetDir, file.toFile)
    }
    FileVisitResult.CONTINUE
  }

  private def processFile(outputDir: File, f: File): Unit = {
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
