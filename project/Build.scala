import sbt._
import Keys._

import com.typesafe.sbt.SbtProguard.{ Proguard, proguardSettings }
import com.typesafe.sbt.SbtProguard.ProguardKeys.{ filteredInputs, inputs, options, proguardVersion }
import com.typesafe.sbt.SbtProguard.ProguardOptions.{ keepMain, noFilter }

object AppBuild extends Build {

  val main = "com.github.pawelkrol.CPU6502.Application"

  val appName = "cpu-6502-simulator"

  val appVersion = "0.01-SNAPSHOT"

  lazy val cpu6502Simulator = Project(
    id = appName,
    base = file("."),
    settings = Defaults.defaultSettings ++ proguardSettings ++ Seq(
      javaOptions += "-Xmx1G",
      filteredInputs in Proguard <++= (packageBin in Compile) map noFilter,
      inputs in Proguard <<= (dependencyClasspath in Compile) map { _.files },
      options in Proguard += keepMain(main),
      options in Proguard += "-dontnote",
      options in Proguard += "-dontobfuscate",
      // options in Proguard += "-dontoptimize",
      options in Proguard += "-dontwarn",
      // options in Proguard += "-ignorewarnings",
      options in Proguard += ProguardConf.cpu6502Simulator,
      proguardVersion in Proguard := "5.2.1"
    )
  )
}
