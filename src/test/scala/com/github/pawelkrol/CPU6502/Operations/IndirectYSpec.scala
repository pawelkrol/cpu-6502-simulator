package com.github.pawelkrol.CPU6502
package Operations

class IndirectYSpec extends FunOperationsSpec {

  private var zp: ByteVal = _

  private var yr: ByteVal = _

  private var zpAddr: Seq[ByteVal] = _

  private def setupOpArg(zp: ByteVal, yr: ByteVal, zpAddr: Seq[ByteVal], value: ByteVal) { assignOpArg((zp +: yr +: zpAddr :+ value): _*) }

  describe("(indirect),y addressing mode") {
    testOpCode(OpCode_ORA_INDY) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $00; YR = $02; $0002 = $00, $0003 = $c8") { AC = 0x00; YR = 0x02; ZF = true; SF = false } {
        context("ORA ($02),Y") { zp = 0x02; yr = 0x02; zpAddr = Seq(0x00, 0xc8) } {
          context("$C802 = $00") { setupOpArg(zp, yr, zpAddr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $01") { setupOpArg(zp, yr, zpAddr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $80") { setupOpArg(zp, yr, zpAddr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }

          context("$C802 = $ff") { setupOpArg(zp, yr, zpAddr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0xff) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }
        }
      }
    }

    testOpCode(OpCode_AND_INDY) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $FF; YR = $02; $0002 = $00, $0003 = $c8") { AC = 0xff; YR = 0x02; ZF = false; SF = true } {
        context("AND ($02),Y") { zp = 0x02; yr = 0x02; zpAddr = Seq(0x00, 0xc8) } {
          context("$C802 = $00") { setupOpArg(zp, yr, zpAddr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x00) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $01") { setupOpArg(zp, yr, zpAddr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $80") { setupOpArg(zp, yr, zpAddr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x80) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }


          context("$C802 = $ff") { setupOpArg(zp, yr, zpAddr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0xff) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }
        }
      }
    }

    testOpCode(OpCode_EOR_INDY) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $80; YR = $02; $0002 = $00, $0003 = $c8") { AC = 0x80; YR = 0x02; ZF = false; SF = true } {
        context("EOR ($02),Y") { zp = 0x02; yr = 0x02; zpAddr = Seq(0x00, 0xc8) } {
          context("$C802 = $00") { setupOpArg(zp, yr, zpAddr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $01") { setupOpArg(zp, yr, zpAddr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $80") { setupOpArg(zp, yr, zpAddr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $ff") { setupOpArg(zp, yr, zpAddr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x7f) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }
    }
  }
}
