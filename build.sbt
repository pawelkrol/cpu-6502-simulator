lazy val root = (project in file(".")).settings(
  javaOptions += "-Xmx1G",
  name := "cpu-6502-simulator",
  organization := "com.github.pawelkrol",
  scalaVersion := "2.12.4",
  version := "0.04-SNAPSHOT"
)

maxErrors := 1

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.github.scopt" %% "scopt" % "3.7.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalactic" %% "scalactic" % "3.0.5" % "test"
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

enablePlugins(SbtProguard)

proguardFilteredInputs in Proguard ++= ProguardOptions.noFilter((packageBin in Compile).value)

proguardInputs in Proguard := (dependencyClasspath in Compile).value.files

proguardOptions in Proguard += ProguardOptions.keepMain("com.github.pawelkrol.CPU6502.Application")

proguardOptions in Proguard += ProguardConf.cpu6502Simulator

proguardVersion in Proguard := "5.3.3"
