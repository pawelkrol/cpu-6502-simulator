package com.github.pawelkrol.CPU6502
package Operations

class IndirectXSpec extends FunOperationsSpec {

  private var xr: ByteVal = _

  private var zpAddr: Seq[ByteVal] = _

  private def setupOpArg(zp: ByteVal, xr: ByteVal, zpAddr: Seq[ByteVal], value: ByteVal) { assignOpArg((zp +: xr +: zpAddr :+ value): _*) }

  describe("(indirect,x) addressing mode") {
    testOpCode(OpCode_ORA_INDX) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $00; XR = $02; $0004 = $00, $0005 = $C8") { AC = 0x00; XR = 0x02; ZF = true; SF = false } {
        context("ORA ($02,X)") { zp = 0x02; xr = 0x02; zpAddr = Seq(0x00, 0xc8) } {
          context("$C800 = $00") { setupOpArg(zp, xr, zpAddr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $01") { setupOpArg(zp, xr, zpAddr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $80") { setupOpArg(zp, xr, zpAddr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }

          context("$C800 = $ff") { setupOpArg(zp, xr, zpAddr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0xff) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }
        }
      }
    }

    testOpCode(OpCode_AND_INDX) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $FF; XR = $02; $0004 = $00, $0005 = $C8") { AC = 0xff; XR = 0x02; ZF = false; SF = true } {
        context("AND ($02,X)") { zp = 0x02; xr = 0x02; zpAddr = Seq(0x00, 0xc8) } {
          context("$C800 = $00") { setupOpArg(zp, xr, zpAddr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x00) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $01") { setupOpArg(zp, xr, zpAddr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $80") { setupOpArg(zp, xr, zpAddr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x80) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }


          context("$C800 = $ff") { setupOpArg(zp, xr, zpAddr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0xff) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }
        }
      }
    }

    testOpCode(OpCode_EOR_INDX) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $80; XR = $02; $0004 = $00, $0005 = $C8") { AC = 0x80; XR = 0x02; ZF = false; SF = true } {
        context("EOR ($02,X)") { zp = 0x02; xr = 0x02; zpAddr = Seq(0x00, 0xc8) } {
          context("$C800 = $00") { setupOpArg(zp, xr, zpAddr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $01") { setupOpArg(zp, xr, zpAddr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $80") { setupOpArg(zp, xr, zpAddr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $ff") { setupOpArg(zp, xr, zpAddr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x7f) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }
    }
  }
}
