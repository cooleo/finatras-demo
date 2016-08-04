import sbt.Keys.{artifactPath, libraryDependencies, mainClass, managedClasspath, name, organization, packageBin, resolvers, version}



name := "finatra-example"
organization := "br.eng.rafaelsouza"
version := "1.0"
scalaVersion := "2.11.8"
parallelExecution in ThisBuild := false

lazy val versions = new {
  val finatra = "2.2.0"
  val guice = "4.0"
  val logback = "1.1.7"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "http://maven.twttr.com"
)

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.twitter" %% "finatra-httpclient" % versions.finatra,
  "ch.qos.logback" % "logback-classic" % versions.logback,

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "org.scalatest" %% "scalatest" % "2.2.3" % "test",
  "org.specs2" %% "specs2" % "2.3.12" % "test")


enablePlugins(DockerPlugin)

dockerfile in docker := {
  val jarFile = Keys.`package`.in(Compile, packageBin).value
  val classpath = (managedClasspath in Compile).value
  val mainclass = mainClass.in(Compile, packageBin).value.get
  val libs = "/app/libs"
  val jarTarget = "/app/" + jarFile.name

  new Dockerfile {
    // Use a base image that contain Java
    from("java")
    // Expose port 9990
    expose(9990)

    // Copy all dependencies to 'libs' in the staging directory
    classpath.files.foreach { depFile =>
      val target = file(libs) / depFile.name
      stageFile(depFile, target)
    }
    // Add the libs dir from the
    addRaw(libs, libs)

    // Add the generated jar file
    add(jarFile, jarTarget)
    // The classpath is the 'libs' dir and the produced jar file
    val classpathString = s"$libs/*:$jarTarget"
    // Set the entry point to start the application using the main class
    cmd("java", "-cp", classpathString, mainclass)
  }
}