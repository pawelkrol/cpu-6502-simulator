package com.github.pawelkrol.CPU6502
package Operations

class CompareSpec extends FunOperationsSpec {

  describe("compare memory and accumulator") {
    describe("immediate addressing mode") {
      testOpCode(OpCode_CMP_IMM) {
        it { expect { operation }.toAdvancePC(0x02) }
        it { expect { operation }.toUseCycles(0x02) }
        it { expect { operation }.notToChange { AC } }

        context("AC = $00") { AC = 0x00; SF = false; ZF = true } {
          context("CMP #$00") { assignOpArg(0x00) } {
            it { expect { operation }.toChange { CF }.from(false).to(true) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("CMP #$01") { assignOpArg(0x01) } {
            it { expect { operation }.notToChange { CF } }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }

          context("CMP #$FF") { assignOpArg(0xff) } {
            it { expect { operation }.notToChange { CF } }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.notToChange { SF } }
          }
        }

        context("AC = $01") { AC = 0x01; SF = false; ZF = false } {
          context("CMP #$00") { assignOpArg(0x00) } {
            it { expect { operation }.toChange { CF }.from(false).to(true) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("CMP #$01") { assignOpArg(0x01) } {
            it { expect { operation }.toChange { CF }.from(false).to(true) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.notToChange { SF } }
          }

          context("CMP #$FF") { assignOpArg(0xff) } {
            it { expect { operation }.notToChange { CF } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }
        }

        context("AC = $FF") { AC = 0xff; SF = true; ZF = false } {
          context("CMP #$00") { assignOpArg(0x00) } {
            it { expect { operation }.toChange { CF }.from(false).to(true) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("CMP #$01") { assignOpArg(0x01) } {
            it { expect { operation }.toChange { CF }.from(false).to(true) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("CMP #$FF") { assignOpArg(0xff) } {
            it { expect { operation }.toChange { CF }.from(false).to(true) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }
    }

    describe("zeropage addressing mode") {
      testOpCode(OpCode_CMP_ZP) {
        // TODO
      }
    }

    describe("zeropage,x addressing mode") {
      testOpCode(OpCode_CMP_ZPX) {
        // TODO
      }
    }

    describe("absolute addressing mode") {
      testOpCode(OpCode_CMP_ABS) {
        // TODO
      }
    }

    describe("absolute,x addressing mode") {
      testOpCode(OpCode_CMP_ABSX) {
        // TODO
      }
    }

    describe("absolute,y addressing mode") {
      testOpCode(OpCode_CMP_ABSY) {
        // TODO
      }
    }

    describe("(indirect,x) addressing mode") {
      testOpCode(OpCode_CMP_INDX) {
        // TODO
      }
    }

    describe("(indirect),y addressing mode") {
      testOpCode(OpCode_CMP_INDY) {
        // TODO
      }
    }
  }
}
