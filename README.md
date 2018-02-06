CPU 6502 Simulator
==================

`cpu-6502-simulator` is a complete MOS 6502 CPU simulator entirely written in [Scala](http://www.scala-lang.org/). The goal of this project has been to provide a tool for execution of any 6502-compiled code in a simulation-based environment. It was heavily inspired and is primarily based upon the work done by _Ullrich von Bassewitz_ in his `sim65` program (originally written in plain `C`).

### What is the motivation behind building another MOS 6502 CPU simulator?

1. There has been no such utility written in [Scala](http://www.scala-lang.org/) ever before.
2. Just to treat it as a practice in building [Scala](http://www.scala-lang.org/) applications.
3. As a necessity is the mother of all invention, and since I am aiming at developing a complete unit-testing framework targetting MOS 6502 CPU (specifically any code that is meant to run on the [Commodore 64](https://en.wikipedia.org/wiki/Commodore_64)) to help me with testing of complex subroutine executions, writing this simulator might be considered an interim step of a larger development process (while still designed to be usable as a standalone tool, this simulator is not an ultimate goal of my work). Another potential utility of this library is to ease implementation of a 6502 disassembler of binary programs.

`cpu-6502-simulator` is not restricted to be executed as a standalone application JAR, although it leaves this possibility (without too many options to configure simulation environment however). It comes up bundled with the minimal set of dependencies required to build, test and include it in your own [Scala](http://www.scala-lang.org/) projects.

PREREQUISITES
-------------

This `cpu-6502-simulator` application is setup with [sbt 0.13.12](http://www.scala-sbt.org/) as a build tool, [sbt-proguard 0.2.2](https://github.com/sbt/sbt-proguard) as an sbt [plugin](http://www.scala-sbt.org/0.13/docs/Plugins.html) for running [ProGuard 5.2.1](http://proguard.sourceforge.net/), [ScalaTest 3.0.0](http://www.scalatest.org/) as a unit-testing framework, [Scalactic 3.0.0](http://www.scalactic.org/) as a small library of utilities, [scopt 3.4.0](https://github.com/scopt/scopt) as a command line options parsing library, [Scala Logging 3.5.0](https://github.com/typesafehub/scala-logging) as a logging library wrapping [SLF4J](http://www.slf4j.org/), and [Logback 1.1.7](http://logback.qos.ch/) as a backend logging framework.

Dependency management is normally handled automatically by your build tool.

`cpu-6502-simulator` may optionally be packaged as a standalone executable JAR using `proguard:proguard` command of an `sbt` shell.

VERSION
-------

Version 0.03-SNAPSHOT (2018-02-06)

INSTALLATION
------------

You can automatically download and install this library by adding the following dependency information to your `build.sbt` configuration file:

    resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

    libraryDependencies += "com.github.pawelkrol" % "cpu-6502-simulator" % "0.03-SNAPSHOT"

In order to build and run an executable application JAR type the following:

    $ git clone git://github.com/pawelkrol/cpu-6502-simulator.git
    $ cd cpu-6502-simulator/
    $ sbt clean update compile test package proguard:proguard

    $ java -Dfile.encoding=UTF8 -jar target/proguard/cpu-6502-simulator-0.03-SNAPSHOT.jar

You may have to add the following automatic export to your `~/.bash_profile`:

    export _JAVA_OPTIONS="-Xms1024m -Xmx2G -Xss256m"

In order to publish an artifact to [Sonatype](https://oss.sonatype.org/) type the following:

    $ sbt publishSigned publishLocal

EXAMPLES
--------

Load program from a file on disk:

    import com.github.pawelkrol.CPU6502.ByteVal
    import com.github.pawelkrol.CPU6502.Core

    import io.Codec.ISO8859
    import io.Source.fromFile

    val core = Core()

    val fileName = "main.prg"
    val targetAddress = 0x1000.toShort

    val data = fromFile(fileName)(ISO8859).toArray.map(ByteVal(_))
    core.memory.write(targetAddress, data)

Write compiled code directly into a memory:

    import com.github.pawelkrol.CPU6502.Core

    val core = Core()

    val targetAddress = 0x1000.toShort

    /** LDY #$00
     *  LDA ($FB),Y
     *  STA ($FD),Y
     *  RTS
     */
    core.memory.write(targetAddress, 0xa0, 0x00, 0xb1, 0xfb, 0x91, 0xfd, 0x60)

Run simulator until 100 cycles count is reached:

    import com.github.pawelkrol.CPU6502.Core
    import com.github.pawelkrol.CPU6502.Runner

    import java.io.File

    val core = Core()
    core.reset
    core.register.PC = 0x1000

    val cycleCount = 100
    val file = new File("main.prg")

    Runner.go(file, Some(cycleCount))

Apply the same command that additionally produces verbose output:

    import com.github.pawelkrol.CPU6502.Application
    import com.github.pawelkrol.CPU6502.Core
    import com.github.pawelkrol.CPU6502.Runner

    import java.io.File

    val core = Core()
    core.reset
    core.register.PC = 0x1000

    val cycleCount = 100
    val file = new File("main.prg")

    Application.verbose = true

    Runner.go(file, Some(cycleCount))

COPYRIGHT AND LICENCE
---------------------

Copyright (C) 2016-2018 by Pawel Krol.

This library is free open source software; you can redistribute it and/or modify it under [the same terms](https://github.com/pawelkrol/cpu-6502-simulator/blob/master/LICENSE.md) as Scala itself, either Scala version 2.12.4 or, at your option, any later version of Scala you may have available.
