lazy val root = (project in file(".")).settings(
  javaOptions += "-Xmx1G",
  name := "cpu-6502-simulator",
  organization := "com.github.pawelkrol",
  scalaVersion := "3.1.3",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  version := "0.06-SNAPSHOT"
)

maxErrors := 1

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  "com.github.scopt" %% "scopt" % "4.1.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "org.scalatest" %% "scalatest" % "3.2.13" % "test",
  "org.scalactic" %% "scalactic" % "3.2.13" % "test"
)

// Disable using the Scala version in output paths and artifacts:
crossPaths := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

Test / publishArtifact := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/pawelkrol/cpu-6502-simulator</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>https://www.apache.org/licenses/LICENSE-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git://github.com/pawelkrol/cpu-6502-simulator</url>
    <connection>scm:git:git://github.com/pawelkrol/cpu-6502-simulator.git</connection>
  </scm>
  <developers>
    <developer>
      <id>pawelkrol</id>
      <name>Pawel Krol</name>
      <url>https://github.com/pawelkrol/cpu-6502-simulator</url>
    </developer>
  </developers>
)

enablePlugins(SbtProguard)

Proguard / proguardFilteredInputs ++= ProguardOptions.noFilter((Compile / packageBin).value)

Proguard / proguardInputs := (Compile / dependencyClasspath).value.files

Proguard / proguardOptions += ProguardOptions.keepMain("com.github.pawelkrol.CPU6502.Application")

Proguard / proguardOptions += ProguardConf.cpu6502Simulator

Proguard / proguardVersion := "7.2.2"
