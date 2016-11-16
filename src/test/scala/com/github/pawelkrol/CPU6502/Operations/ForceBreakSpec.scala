package com.github.pawelkrol.CPU6502

class ForceBreakSpec extends FunOperationsSpec {

  describe("immediate addressing mode") {
    testOpCode(OpCode_BRK_IMP) {
      context("$FFFE/$FFFF = $FF48") { memoryWrite(0xfffe, 0x48); memoryWrite(0xffff, 0xff) } {
        it("sets PC to $FFFE/FFFF vector") { expect { operation }.toSetPC(0xff48.toShort) }
        it("uses 7 CPU cycles") { expect { operation }.toUseCycles(0x07) }
        it("pushes 3 bytes onto stack") { expect { operation }.toChange { SP }.from(0xf9).to(0xf6) }

        it("sets break flag") { expect { operation }.toChange { BF }.to(true) }
        it("sets interrupt flag") { expect { operation }.toChange { IF }.to(true) }

        it("pushes program counter + 2 to stack") {
          expect { operation }.toChange { memoryRead(0x01f8) }.to(0x02)
          expect { operation }.toChange { memoryRead(0x01f9) }.to(0xc0)
        }

        it("pushes status register to stack") {
          expect { operation }.toChange { memoryRead(0x01f7) }.to(0x30)
        }
      }
    }
  }
}
