package com.github.pawelkrol.CPU6502
package Operations

class AbsoluteSpec extends FunOperationsSpec {

  private var addr: Short = _

  private def setupOpArg(address: Short, value: ByteVal): Unit = { assignOpArg((Util.addr2ByteVals(address) :+ value): _*) }

  describe("absolute addressing mode") {
    testOpCode(OpCode_ORA_ABS) {
      it { expect { operation }.toAdvancePC(0x03) }

      context("AC = $00") { AC = 0x00; ZF = true; SF = false } {
        context("ORA $C800") { addr = 0xc800.toShort } {
          context("$C800 = $00") { setupOpArg(addr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $01") { setupOpArg(addr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $80") { setupOpArg(addr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }

          context("$C800 = $ff") { setupOpArg(addr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0xff) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }
        }
      }
    }

    testOpCode(OpCode_AND_ABS) {
      it { expect { operation }.toAdvancePC(0x03) }

      context("AC = $ff") { AC = 0xff; ZF = false; SF = true } {
        context("AND $C800") { addr = 0xc800.toShort } {
          context("$C800 = $00") { setupOpArg(addr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x00) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $01") { setupOpArg(addr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $80") { setupOpArg(addr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x80) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $ff") { setupOpArg(addr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0xff) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }
        }
      }
    }

    testOpCode(OpCode_EOR_ABS) {
      it { expect { operation }.toAdvancePC(0x03) }

      context("AC = $80") { AC = 0x80; ZF = false; SF = true } {
        context("EOR $C800") { addr = 0xc800.toShort } {
          context("$C800 = $00") { setupOpArg(addr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $01") { setupOpArg(addr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $80") { setupOpArg(addr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $ff") { setupOpArg(addr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x7f) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }
    }

    testOpCode(OpCode_BIT_ABS) {
      it { expect { operation }.toAdvancePC(0x03) }

      context("AC = $80") { AC = 0x80; ZF = false; OF = false; SF = true } {
        context("BIT $C800") { addr = 0xc800.toShort } {
          context("$C800 = $00") { setupOpArg(addr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.notToChange { OF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $01") { setupOpArg(addr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x01) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.notToChange { OF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $40") { setupOpArg(addr, 0x40) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x40) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { OF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C800 = $80") { setupOpArg(addr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0x80) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { OF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C800 = $ff") { setupOpArg(addr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc800) == 0xff) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { OF }.from(false).to(true) }
            it { expect { operation }.notToChange { SF } }
          }
        }
      }
    }
  }
}
