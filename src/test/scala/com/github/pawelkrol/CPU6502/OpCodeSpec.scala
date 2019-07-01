package com.github.pawelkrol.CPU6502

class OpCodeSpec extends FunSpec {

  private var core: Core = _

  private var opCode: ByteVal = _

  before {
    core = Core()
  }

  describe("argument value") {
    val argValues = Map[Int, String](
      0x00 -> "",        // BRK
      0x01 -> "($FF,X)", // ORA ($FF,X)
      0x05 -> "$FF",     // ORA $FF
      0x06 -> "$FF",     // ASL $FF
      0x08 -> "",        // PHP
      0x09 -> "#$FF",    // ORA #$FF
      0x0a -> "A",       // ASL A
      0x0d -> "$FFFF",   // ORA $FFFF
      0x0e -> "$FFFF",   // ASL $FFFF
      0x10 -> "$1001",   // BPL $1001
      0x11 -> "($FF),Y", // ORA ($FF),Y
      0x15 -> "$FF,X",   // ORA $FF,X
      0x16 -> "$FF,X",   // ASL $FF,X
      0x18 -> "",        // CLC
      0x19 -> "$FFFF,Y", // ORA $FFFF,Y
      0x1d -> "$FFFF,X", // ORA $FFFF,X
      0x1e -> "$FFFF,X", // ASL $FFFF,X
      0x60 -> "",        // RTS
      0x61 -> "($FF,X)", // ADC ($FF,X)
      0x65 -> "$FF",     // ADC $FF
      0x66 -> "$FF",     // ROR $FF
      0x68 -> "",        // PLA
      0x69 -> "#$FF",    // ADC #$FF
      0x6a -> "A",       // ROR A
      0x6c -> "($FFFF)", // JMP ($FFFF)
      0x6d -> "$FFFF",   // ADC $FFFF
      0x6e -> "$FFFF",   // ROR $FFFF
      0x70 -> "$1001",   // BVS $1001
      0x71 -> "($FF),Y", // ADC ($FF),Y
      0x75 -> "$FF,X",   // ADC $FF,X
      0x76 -> "$FF,X",   // ROR $FF,X
      0x78 -> "",        // SEI
      0x79 -> "$FFFF,Y", // ADC $FFFF,Y
      0x7d -> "$FFFF,X", // ADC $FFFF,X
      0x7e -> "$FFFF,X"  // ROR $FFFF,X
    )

    context("PC = $1000") { core.register.PC = 0x1000 } {
      argValues.foreach({ case (value, argValue) => {
        val opCode = OpCode(value, core)
        context("$1000 = $%02X; $1001 = $FF; $1002 = $FF".format(value, opCode.symName)) { core.memory.write(0x1000, value, 0xff, 0xff) } {
          it("%s %s".format(opCode.symName, argValue)) {
            assert(opCode.argValue(core) === argValue)
          }
        }
      } })
    }
  }

  describe("illegal opcode") {
    context("PC = $1000") { core.register.PC = 0x1000 } {
      context("$1000 = $02") { opCode = 0x02 } {
        it("throws an illegal opcode error") {
          val caught = intercept[IllegalOpCodeError] {
            OpCode(opCode, core)
          }
          assert(caught.getMessage === "Illegal opcode $02 at address $1000")
        }
      }
    }
  }
}
