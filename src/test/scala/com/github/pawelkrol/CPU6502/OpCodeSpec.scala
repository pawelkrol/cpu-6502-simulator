package com.github.pawelkrol.CPU6502

class OpCodeSpec extends FunFunSpec {

  private var register: Register = _

  private var opCode: ByteVal = _

  before {
    register = Application.core.register
  }

  describe("illegal opcode") {
    context("PC = $1000") { register.PC = 0x1000 } {
      context("$1000 = $02") { opCode = 0x02 } {
        it("throws an illegal opcode error") {
          val caught = intercept[IllegalOpCodeError] {
            OpCode(opCode)
          }
          assert(caught.getMessage === "Illegal opcode $02 at address $1000")
        }
      }
    }
  }
}
