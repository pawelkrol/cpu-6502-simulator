CPU 6502 Simulator (WORK-IN-PROGRESS)
=====================================

`cpu-6502-simulator` is a complete MOS 6502 CPU simulator entirely written in [Scala](http://www.scala-lang.org/). The goal of this project has been to provide a tool for execution of any 6502-compiled code in a simulation-based environment. It was heavily inspired and is based upon the work done by Ullrich von Bassewitz in his `sim65` program (originally written in plain `C`).

### What is the motivation behind building another MOS 6502 CPU simulator?

1. There has been no such utility written in [Scala](http://www.scala-lang.org/) ever before.
2. Simply to practice building [Scala](http://www.scala-lang.org/) applications.
3. As necessity is the mother of invention, and since I am aiming at developing a complete unit-testing framework targetting MOS 6502 CPU (specifically any code that is meant to run on the [Commodore 64](https://en.wikipedia.org/wiki/Commodore_64)) to help with testing of any complex subroutines, writing a simulator might be considered an interim step in the entire process (while designed to be usable as a standalone tool, this simulator is still not an ultimate goal of my work).

`cpu-6502-simulator` is not meant to be executed as a standalone application JAR. It comes up bundled with the minimal set of dependencies required to build, test and include it in your own projects.

This `cpu-6502-simulator` application is setup with [sbt 0.13.12](http://www.scala-sbt.org/) as a build tool, [sbt-proguard 0.2.2](https://github.com/sbt/sbt-proguard) as an sbt [plugin](http://www.scala-sbt.org/0.13/docs/Plugins.html) for running [ProGuard 5.3](http://proguard.sourceforge.net/), [ScalaTest 3.0.0](http://www.scalatest.org/) as a unit-testing framework, [Scalactic 3.0.0](http://www.scalactic.org/) as a small library of utilities, [scopt 3.4.0](https://github.com/scopt/scopt) as a command line options parsing library, [Scala Logging 3.5.0](https://github.com/typesafehub/scala-logging) as a logging library wrapping [SLF4J](http://www.slf4j.org/), and [Logback 1.1.7](http://logback.qos.ch/) as a backend logging framework.

VERSION
-------

Version 0.01-SNAPSHOT (2016-10-09)

INSTALLATION
------------

Add the following automatic export to your `~/.bash_profile`:

    export _JAVA_OPTIONS="-Xms1024m -Xmx2G -Xss256m"

In order to build and run an application JAR type the following:

    $ git clone git://github.com/pawelkrol/cpu-6502-simulator.git
    $ cd cpu-6502-simulator/
    $ sbt clean update compile test package proguard:proguard

    $ java -Dfile.encoding=UTF8 -jar target/scala-2.11/proguard/cpu-6502-simulator-0.01-SNAPSHOT.jar

COPYRIGHT AND LICENCE
---------------------

Copyright (C) 2016 by Pawel Krol.

This library is free open source software; you can redistribute it and/or modify it under [the same terms](https://github.com/pawelkrol/cpu-6502-simulator/blob/master/LICENSE.md) as Scala itself, either Scala version 2.11.8 or, at your option, any later version of Scala you may have available.