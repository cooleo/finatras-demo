import sbt._

object PackageSprayBuild extends Build {
  lazy val root = Project("finatras-demo", file(".")) dependsOn docker
  lazy val docker = file("../..").getAbsoluteFile.toURI
}
