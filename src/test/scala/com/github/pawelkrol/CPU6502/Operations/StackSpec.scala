package com.github.pawelkrol.CPU6502
package Operations

class StackSpec extends FunOperationsSpec {

  describe("implied/stack addressing mode") {
    testOpCode(OpCode_PHP) {
      it("advances PC by 1 byte") { expect { operation }.toAdvancePC(0x01) }
      it("uses 3 CPU cycles") { expect { operation }.toUseCycles(0x03) }
      it("pushes 1 byte onto stack") { expect { operation }.toChange { SP }.from(0xf9).to(0xf8) }

      context("SR = %00100000") { BF = false; IF = false } {
        it("pushes processor status on stack") {
          expect { operation }.toChange { memoryRead(0x01f9) }.from(0xff).to(0x20)
        }
      }

      context("SR = %00110100") { BF = true; IF = true } {
        it("pushes processor status on stack") {
          expect { operation }.toChange { memoryRead(0x01f9) }.from(0xff).to(0x24)
        }
      }
    }

    testOpCode(OpCode_PLP) {
      it("advances PC by 1 byte") { expect { operation }.toAdvancePC(0x01) }
      it("uses 4 CPU cycles") { expect { operation }.toUseCycles(0x04) }
      it("pulls 1 byte from stack") { expect { operation }.toChange { SP }.from(0xf9).to(0xfa) }

      context("$01fa = %00100000") { memoryWrite(0x01fa, 0x20) } {
        it("pulls processor status from stack") {
          expect { operation }.toChange { SR }.from(0x20).to(0x20)
        }
      }

      context("$01fa = %00110100") { memoryWrite(0x01fa, 0x24) } {
        it("pulls processor status from stack") {
          expect { operation }.toChange { SR }.from(0x20).to(0x24)
        }
      }
    }
  }
}
