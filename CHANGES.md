CHANGES
=======

Revision history for `cpu-6502-simulator`, a complete MOS 6502 CPU simulator entirely written in [Scala](http://www.scala-lang.org/).

0.04 (2018-02-27)
-----------------

* Enhancement: Expose direct access to Charset Generator and VIC data
* Enhancement: Ensure VIC data of new `CommodoreMemory` instances is always initialised with Kernal's I/O defaults
* `scala-logging` version upgraded to 3.8.0

0.03 (2018-02-07)
-----------------

* Enhancement: Enable customisation of a simulated program's memory by replacing `Memory` class with a generic trait that a user's program may extend from
* New feature: Provide an implementation of `CommodoreMemory` which may be used as a replacement of `Memory` object specified upon `Core` initialisation (it uses Kernal, Charset Generator, and Basic binaries, selecting an appropriate memory module based on a value change applied to `$01` I/O register)
* New feature: Provide an implementation of `ExtendedMemory` which is capable of simulating +60k RAM memory expansion
* `Scala` version upgraded to 2.12.4

0.02 (2017-01-01)
-----------------

* Refactoring: Allow instantiation of multiple `Core` objects by removing all references to a single `Core` instance accessible globally in the main `Application` object (previous implementation resulted in inability to instantiate a standalone `Core` object and its tight coupling with the main `Application` object)
* Bug fixed: Add 1 to program counter pulled from stack when returning from subroutine (previous implementation resulted in broken RTS operation)
* Bug fixed: Repair broken packaging while publishing artifact to remote repository (previous implementation resulted in creating application and javadoc JARs overwritten with contents of sources JAR)
* `Scala` version upgraded to 2.12.1

0.01 (2016-11-16)
-----------------

* Initial version (supports all documented MOS 6502 opcodes, provides step-by-step execution of an assembled source code, exposes core system with a direct access to all CPU registers and an entire addressable memory, and implements experimental command line simulator enabling execution of an arbitrary program file with a 6502-compiled code)
