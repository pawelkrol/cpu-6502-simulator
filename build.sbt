import com.typesafe.sbt.SbtProguard.ProguardKeys.{ filteredInputs, inputs, options, proguardVersion }
import com.typesafe.sbt.SbtProguard.ProguardOptions.{ keepMain, noFilter }

lazy val root = (project in file(".")).settings(
  javaOptions += "-Xmx1G",
  name := "cpu-6502-simulator",
  organization := "com.github.pawelkrol",
  scalaVersion := "2.12.1",
  version := "0.03-SNAPSHOT"
)

maxErrors := 1

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.8",
  "com.github.scopt" %% "scopt" % "3.5.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalactic" %% "scalactic" % "3.0.1" % "test"
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

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/pawelkrol/cpu-6502-simulator</url>
  <licenses>
    <license>
      <name>Scala License</name>
      <url>http://www.scala-lang.org/node/146</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git://github.com/pawelkrol/cpu-6502-simulator.git</url>
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

proguardSettings

ProguardKeys.filteredInputs in Proguard <++= (packageBin in Compile) map noFilter

ProguardKeys.inputs in Proguard := { (dependencyClasspath in Compile).value.files }

ProguardKeys.options in Proguard += keepMain("com.github.pawelkrol.CPU6502.Application")

ProguardKeys.options in Proguard += ProguardConf.cpu6502Simulator

ProguardKeys.proguardVersion in Proguard := "5.3.2"
