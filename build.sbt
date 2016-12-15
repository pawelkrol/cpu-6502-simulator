scalacOptions += "-feature"

scalaVersion := "2.11.8"

version := "0.02-SNAPSHOT"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.github.scopt" %% "scopt" % "3.4.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalactic" %% "scalactic" % "3.0.0" % "test"
)

// Disable using the Scala version in output paths and artifacts:
crossPaths := false

publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

name := "cpu-6502-simulator"

organization := "com.github.pawelkrol"

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
