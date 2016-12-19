CHANGES
=======

Revision history for `cpu-6502-simulator`, a complete MOS 6502 CPU simulator entirely written in [Scala](http://www.scala-lang.org/).

0.02-SNAPSHOT (2016-12-19)
--------------------------

* Refactoring: Allow instantiation of multiple `Core` objects by removing all references to a single `Core` instance accessible globally in the main `Application` object (previous implementation resulted in inability to instantiate a standalone `Core` object and its tight coupling with the main `Application` object)
* Bug fixed: Add 1 to program counter pulled from stack when returning from subroutine (previous implementation resulted in broken RTS operation)
* Bug fixed: Repair broken packaging while publishing artifact to remote repository (previous implementation resulted in creating application and javadoc JARs overwritten with contents of sources JAR)

0.01 (2016-11-16)
-----------------

* Initial version (supports all documented MOS 6502 opcodes, provides step-by-step execution of an assembled source code, exposes core system with a direct access to all CPU registers and an entire addressable memory, and implements experimental command line simulator enabling execution of an arbitrary program file with a 6502-compiled code)
