package com.github.pawelkrol.CPU6502
package Operations

class ReturnSpec extends FunOperationsSpec {

  private def assertSetPC(stack: Int) {
    context("$%04X = $00, $%04X = $C8".format(stack, stack + 1)) { memoryWrite(stack, 0x00); memoryWrite(stack + 1, 0xc8) } {
      it("sets PC to $C800") { expect { operation }.toSetPC(0xc800.toShort) }
    }
  }

  private def assertSetSR {
    context("$01FA = %00110110") { memoryWrite(0x01fa, 0x35) } {
      it("sets SR to $35") { expect { operation }.toSetSR(0x35) }
    }
  }

  describe("return from interrupt") {
    describe("implied/stack addressing mode") {
      testOpCode(OpCode_RTI) {
        it("uses 6 CPU cycles") { expect { operation }.toUseCycles(0x06) }
        it("pulls 3 bytes from stack") { expect { operation }.toChange { SP }.from(0xf9).to(0xfc) }

        assertSetPC(stack = 0x01fb)
        assertSetSR
      }
    }
  }

  describe("return from subroutine") {
    describe("implied/stack addressing mode") {
      testOpCode(OpCode_RTS) {
        it("uses 6 CPU cycles") { expect { operation }.toUseCycles(0x06) }
        it("pulls 2 bytes from stack") { expect { operation }.toChange { SP }.from(0xf9).to(0xfb) }

        assertSetPC(stack = 0x01fa)
      }
    }
  }
}
