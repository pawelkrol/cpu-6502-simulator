package com.github.pawelkrol.CPU6502
package Operations

class ZeroPageXSpec extends FunOperationsSpec {

  private var xr: ByteVal = _

  describe("zeropage,x addressing mode") {
    testOpCode(OpCode_ORA_ZPX) {
      it { expect2 { operation }.toAdvancePC(0x02) }

      context("AC = $00; XR = $02") { AC = 0x00; XR = 0x02; ZF = true; SF = false } {
        context("ORA $02,X") { zp = 0x02; xr = 0x02 } {
          context("$0004 = $00") { assignOpArg(zp, xr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x00) }
            it { expect2 { operation }.notToChange { AC } }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$0004 = $01") { assignOpArg(zp, xr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x01) }
            it { expect2 { operation }.toChange { AC }.from(0x00).to(0x01) }
            it { expect2 { operation }.toChange { ZF }.from(true).to(false) }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$0004 = $80") { assignOpArg(zp, xr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x80) }
            it { expect2 { operation }.toChange { AC }.from(0x00).to(0x80) }
            it { expect2 { operation }.toChange { ZF }.from(true).to(false) }
            it { expect2 { operation }.toChange { SF }.from(false).to(true) }
          }

          context("$0004 = $FF") { assignOpArg(zp, xr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0xff) }
            it { expect2 { operation }.toChange { AC }.from(0x00).to(0xff) }
            it { expect2 { operation }.toChange { ZF }.from(true).to(false) }
            it { expect2 { operation }.toChange { SF }.from(false).to(true) }
          }
        }
      }
    }

    testOpCode(OpCode_AND_ZPX) {
      it { expect2 { operation }.toAdvancePC(0x02) }

      context("AC = $FF; XR = $02") { AC = 0xff; XR = 0x02; ZF = false; SF = true } {
        context("AND $02,X") { zp = 0x02; xr = 0x02 } {
          context("$0004 = $00") { assignOpArg(zp, xr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x00) }
            it { expect2 { operation }.toChange { AC }.from(0xff).to(0x00) }
            it { expect2 { operation }.toChange { ZF }.from(false).to(true) }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0004 = $01") { assignOpArg(zp, xr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x01) }
            it { expect2 { operation }.toChange { AC }.from(0xff).to(0x01) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0004 = $80") { assignOpArg(zp, xr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x80) }
            it { expect2 { operation }.toChange { AC }.from(0xff).to(0x80) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$0004 = $FF") { assignOpArg(zp, xr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0xff) }
            it { expect2 { operation }.notToChange { AC } }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }
        }
      }
    }

    testOpCode(OpCode_EOR_ZPX) {
      it { expect2 { operation }.toAdvancePC(0x02) }

      context("AC = $80; XR = $02") { AC = 0x80; XR = 0x02; ZF = false; SF = true } {
        context("EOR $02,X") { zp = 0x02; xr = 0x02 } {
          context("$0004 = $00") { assignOpArg(zp, xr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x00) }
            it { expect2 { operation }.notToChange { AC } }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$0004 = $01") { assignOpArg(zp, xr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x01) }
            it { expect2 { operation }.toChange { AC }.from(0x80).to(0x81) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$0004 = $80") { assignOpArg(zp, xr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0x80) }
            it { expect2 { operation }.toChange { AC }.from(0x80).to(0x00) }
            it { expect2 { operation }.toChange { ZF }.from(false).to(true) }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0004 = $FF") { assignOpArg(zp, xr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0x0004)() == 0xff) }
            it { expect2 { operation }.toChange { AC }.from(0x80).to(0x7f) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }
    }
  }
}
