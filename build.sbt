scalacOptions += "-feature"

lazy val root = (project in file(".")).
  settings(
    name := "cpu-6502-simulator",
    version := "0.01-SNAPSHOT",
    scalaVersion := "2.11.8"
  )

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.github.scopt" %% "scopt" % "3.4.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalactic" %% "scalactic" % "3.0.0" % "test"
)

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "-" + module.revision + "." + artifact.extension
}

proguardSettings

ProguardKeys.filteredInputs in Proguard <++= (packageBin in Compile) map ProguardOptions.noFilter

ProguardKeys.inputs in Proguard <<= (dependencyClasspath in Compile) map { _.files }

ProguardKeys.options in Proguard += ProguardOptions.keepMain("com.github.pawelkrol.cpu6502.Application")

ProguardKeys.options in Proguard += ProguardConf.cpu6502Simulator

ProguardKeys.proguardVersion in Proguard := "5.3"