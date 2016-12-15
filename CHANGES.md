CHANGES
=======

Revision history for `cpu-6502-simulator`, a complete MOS 6502 CPU simulator entirely written in [Scala](http://www.scala-lang.org/).

0.02-SNAPSHOT (2016-12-15)
--------------------------

* Bug fixed: Add 1 to program counter pulled from stack when returning from subroutine

0.01 (2016-11-16)
-----------------

* Initial version (supports all documented MOS 6502 opcodes, provides step-by-step execution of an assembled source code, exposes core system with a direct access to all CPU registers and an entire addressable memory, and implements experimental command line simulator enabling execution of an arbitrary program file with a 6502-compiled code)
