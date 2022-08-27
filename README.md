CPU 6502 Simulator
==================

`cpu-6502-simulator` is a complete MOS 6502 CPU simulator natively developed in [Scala]. The goal of this project has been to provide a tool for execution of any 6502-compiled code in a simulation-based environment. It was heavily inspired and is primarily based upon the work done by _Ullrich von Bassewitz_ in his `sim65` program (originally written in plain `C`).

### What was the motivation behind building another MOS 6502 CPU simulator?

1. There has been no such utility written in [Scala] ever before.
2. Just to treat it as a practice in building [Scala] applications.
3. As a necessity is the mother of all invention, and since back then I was aiming at developing a complete [unit-testing framework] targetting MOS 6502 CPU (specifically any code that was meant to run on the [Commodore 64]) to help me with testing of complex subroutine executions, I considered writing this simulator an interim step of a larger development process (while still designed to be usable as a standalone tool, this simulator had not been an ultimate goal of my work). Another potential utility of this library is to ease implementation of a 6502 disassembler of binary programs.

`cpu-6502-simulator` is not restricted to be executed as a standalone application JAR, although it leaves this possibility (without too many options to configure simulation environment however). It comes up bundled with the minimal set of dependencies required to build, test and include it in your own [Scala 3] projects.

PREREQUISITES
-------------

This `cpu-6502-simulator` application is setup with [sbt 1.7.1] as a build tool, [sbt-proguard 0.5.0] as an sbt [plugin] for running [ProGuard 7.2.2], [ScalaTest 3.2.13] as a unit-testing framework, [Scalactic 3.2.13] as a small library of utilities, [scopt 4.1.0] as a command line options parsing library, [Scala Logging 3.9.5] as a logging library wrapping [SLF4J], and [Logback 1.2.11] as a backend logging framework.

Dependency management is normally handled automatically by your build tool.

`cpu-6502-simulator` may optionally be packaged as a standalone executable JAR using `proguard:proguard` command of an `sbt` shell.

VERSION
-------

Version 1.0.0 (2022-08-27)

INSTALLATION
------------

You can automatically download and install this library by adding the following dependency information to your `build.sbt` configuration file:

    resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

    libraryDependencies += "com.github.pawelkrol" % "cpu-6502-simulator" % "1.0.0"

In order to build and run an executable application JAR type the following:

    $ git clone git://github.com/pawelkrol/cpu-6502-simulator.git
    $ cd cpu-6502-simulator/
    $ sbt clean update compile test package proguard:proguard

    $ java -Dfile.encoding=UTF8 -jar target/proguard/cpu-6502-simulator-1.0.0.jar --help

You may have to add the following automatic export to your `~/.bash_profile`:

    export _JAVA_OPTIONS="-Xms1024m -Xmx2G -Xss256m"

In order to publish an artifact to [Sonatype] type the following:

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

    Runner.go(core, file, Some(cycleCount))

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

    Runner.go(core, file, Some(cycleCount))

Simulate running a program using +60k RAM memory expansion:

    import com.github.pawelkrol.CPU6502.Commodore.ExtendedMemory
    import com.github.pawelkrol.CPU6502.Core

    val memory = ExtendedMemory()
    val core = Core(memory)

    /** Use the following switches in a source code of a simulated program:
     *
     *  ; Switch $1000-$ffff address space to an additional memory bank:
     *  LDA #$80
     *  STA $D100
     *
     *  ; Switch $1000-$ffff address space to an onboard memory bank:
     *  LDA #$00
     *  STA $D100
     */

    // Runner.go(core, file, Some(cycleCount))

COPYRIGHT AND LICENCE
---------------------

Copyright (C) 2016-2022 by Pawel Krol.

This library is free open source software; you can redistribute it and/or modify it under [the same terms] as Scala itself, either Scala version 3.1.3 or, at your option, any later version of Scala you may have available.

[Scala]: http://www.scala-lang.org/
[unit-testing framework]: https://github.com/pawelkrol/Scala-CommTest
[Commodore 64]: https://en.wikipedia.org/wiki/Commodore_64
[Scala 3]: http://www.scala-lang.org/
[sbt 1.7.1]: http://www.scala-sbt.org/
[sbt-proguard 0.5.0]: https://github.com/sbt/sbt-proguard
[plugin]: http://www.scala-sbt.org/0.13/docs/Plugins.html
[ProGuard 7.2.2]: http://proguard.sourceforge.net/
[ScalaTest 3.2.13]: http://www.scalatest.org/
[Scalactic 3.2.13]: http://www.scalactic.org/
[scopt 4.1.0]: https://github.com/scopt/scopt
[Scala Logging 3.9.5]: https://github.com/typesafehub/scala-logging
[SLF4J]: http://www.slf4j.org/
[Logback 1.2.11]: http://logback.qos.ch/
[Sonatype]: https://oss.sonatype.org/
[the same terms]: https://github.com/pawelkrol/cpu-6502-simulator/blob/master/LICENSE.md
