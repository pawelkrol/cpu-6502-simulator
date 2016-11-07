package com.github.pawelkrol.CPU6502
package Operations

class JumpSpec extends FunOperationsSpec {

  private var addr: Short = _

  private def setupIndOpArg(address: Short, target: Short) { assignOpArg((Util.addr2ByteVals(address) ++ Util.addr2ByteVals(target)): _*) }

  private def assertSetPC(sym: String) {
    context(sym + " $C800") { addr = 0xc800.toShort } {
      context("$C800 = {irrelevant}") { setupAbsOpArg(addr, 0x60) } {
        it("sets PC to $C800") { expect { operation }.toSetPC(addr) }
      }
    }
  }

  describe("jump to new location saving return address") {
    describe("absolute addressing mode") {
      testOpCode(OpCode_JSR_ABS) {
        it("uses 6 CPU cycles") { expect { operation }.toUseCycles(0x06) }
        it("pushes 2 bytes onto stack") { expect { operation }.toChange { SP }.from(0xf9).to(0xf7) }

        it("pushes program counter + 2 to stack") {
          expect { operation }.toChange { memoryRead(0x01f8) }.to(0x02)
          expect { operation }.toChange { memoryRead(0x01f9) }.to(0xc0)
        }

        assertSetPC("JSR")
      }
    }
  }

  describe("jump to new location") {
    describe("absolute addressing mode") {
      testOpCode(OpCode_JMP_ABS) {
        it("uses 3 CPU cycles") { expect { operation }.toUseCycles(0x03) }
        it("does not push any bytes onto stack") { expect { operation }.notToChange { SP } }

        assertSetPC("JMP")
      }
    }

    describe("(indirect) addressing mode") {
      testOpCode(OpCode_JMP_IND) {
        it("uses 5 CPU cycles") { expect { operation }.toUseCycles(0x05) }
        it("does not push any bytes onto stack") { expect { operation }.notToChange { SP } }

        context("JMP ($C800)") { addr = 0xc800.toShort } {
          context("$C800/$C801 = $2900") { setupIndOpArg(addr, 0x2900) } {
            it("meets preconditions") {
              assert(memoryRead(0xc001) == 0x00)
              assert(memoryRead(0xc002) == 0xc8)
              assert(memoryRead(0xc800) == 0x00)
              assert(memoryRead(0xc801) == 0x29)
            }

            it("sets PC to $2900") { expect { operation }.toSetPC(0x2900) }
          }
        }

        context("6502 indirect jump bug") {} {
          context("JMP ($C7FF)") { addr = 0xc7ff.toShort } {
            context("$C7FF/$C800 = $2900") { setupIndOpArg(addr, 0x2900) } {
              it("meets preconditions") {
                assert(memoryRead(0xc001) == 0xff)
                assert(memoryRead(0xc002) == 0xc7)
                assert(memoryRead(0xc700) == 0xff)
                assert(memoryRead(0xc7ff) == 0x00)
                assert(memoryRead(0xc800) == 0x29)
              }

              it("sets PC to $ff00") { expect { operation }.toSetPC(0xff00.toShort) }
            }
          }
        }
      }
    }
  }
}
