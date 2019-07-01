package com.github.pawelkrol.CPU6502
package Operations

class AbsoluteXSpec extends FunOperationsSpec {

  private var addr: Short = _

  private var xr: ByteVal = _

  private def setupOpArg(address: Short, xr: ByteVal, value: ByteVal): Unit = { assignOpArg((Util.addr2ByteVals(address) :+ xr :+ value): _*) }

  describe("absolute,x addressing mode") {
    testOpCode(OpCode_ORA_ABSX) {
      it { expect2 { operation }.toAdvancePC(0x03) }
      it { expect2 { operation }.toUseCycles(0x04) }

      context("AC = $00; XR = $02") { AC = 0x00; XR = 0x02; ZF = true; SF = false } {
        context("ORA $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
          context("$C802 = $00") { setupOpArg(addr, xr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x00) }
            it { expect2 { operation }.notToChange { AC } }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$C802 = $01") { setupOpArg(addr, xr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x01) }
            it { expect2 { operation }.toChange { AC }.from(0x00).to(0x01) }
            it { expect2 { operation }.toChange { ZF }.from(true).to(false) }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$C802 = $80") { setupOpArg(addr, xr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x80) }
            it { expect2 { operation }.toChange { AC }.from(0x00).to(0x80) }
            it { expect2 { operation }.toChange { ZF }.from(true).to(false) }
            it { expect2 { operation }.toChange { SF }.from(false).to(true) }
          }

          context("$C802 = $ff") { setupOpArg(addr, xr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0xff) }
            it { expect2 { operation }.toChange { AC }.from(0x00).to(0xff) }
            it { expect2 { operation }.toChange { ZF }.from(true).to(false) }
            it { expect2 { operation }.toChange { SF }.from(false).to(true) }
          }
        }
      }

      describe("with page cross") {
        context("AC = $00; XR = $02") { AC = 0x00; XR = 0x02 } {
          context("ORA $C8FF,X") { addr = 0xc8ff.toShort; xr = 0x02 } {
            context("$C901 = $01") { setupOpArg(addr, xr, 0x01) } {
              it("meets preconditions") { assert(memoryRead(0xc901)() == 0x01) }
              it { expect2 { operation }.toChange { AC }.from(0x00).to(0x01) }
              it { expect2 { operation }.toUseCycles(0x05) }
            }
          }
        }
      }
    }

    testOpCode(OpCode_AND_ABSX) {
      it { expect2 { operation }.toAdvancePC(0x03) }
      it { expect2 { operation }.toUseCycles(0x04) }

      context("AC = $ff; XR = $02") { AC = 0xff; XR = 0x02; ZF = false; SF = true } {
        context("AND $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
          context("$C802 = $00") { setupOpArg(addr, xr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x00) }
            it { expect2 { operation }.toChange { AC }.from(0xff).to(0x00) }
            it { expect2 { operation }.toChange { ZF }.from(false).to(true) }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $01") { setupOpArg(addr, xr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x01) }
            it { expect2 { operation }.toChange { AC }.from(0xff).to(0x01) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $80") { setupOpArg(addr, xr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x80) }
            it { expect2 { operation }.toChange { AC }.from(0xff).to(0x80) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$C802 = $ff") { setupOpArg(addr, xr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0xff) }
            it { expect2 { operation }.notToChange { AC } }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }
        }
      }

      describe("with page cross") {
        context("AC = $ff; XR = $02") { AC = 0xff; XR = 0x02 } {
          context("AND $C8FF,X") { addr = 0xc8ff.toShort; xr = 0x02 } {
            context("$C901 = $01") { setupOpArg(addr, xr, 0x01) } {
              it("meets preconditions") { assert(memoryRead(0xc901)() == 0x01) }
              it { expect2 { operation }.toChange { AC }.from(0xff).to(0x01) }
              it { expect2 { operation }.toUseCycles(0x05) }
            }
          }
        }
      }
    }

    testOpCode(OpCode_EOR_ABSX) {
      it { expect2 { operation }.toAdvancePC(0x03) }
      it { expect2 { operation }.toUseCycles(0x04) }

      context("AC = $80; XR = $02") { AC = 0x80; XR = 0x02; ZF = false; SF = true } {
        context("EOR $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
          context("$C802 = $00") { setupOpArg(addr, xr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x00) }
            it { expect2 { operation }.notToChange { AC } }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$C802 = $01") { setupOpArg(addr, xr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x01) }
            it { expect2 { operation }.toChange { AC }.from(0x80).to(0x81) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.notToChange { SF } }
          }

          context("$C802 = $80") { setupOpArg(addr, xr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0x80) }
            it { expect2 { operation }.toChange { AC }.from(0x80).to(0x00) }
            it { expect2 { operation }.toChange { ZF }.from(false).to(true) }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $ff") { setupOpArg(addr, xr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802)() == 0xff) }
            it { expect2 { operation }.toChange { AC }.from(0x80).to(0x7f) }
            it { expect2 { operation }.notToChange { ZF } }
            it { expect2 { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }

      describe("with page cross") {
        context("AC = $80; XR = $02") { AC = 0x80; XR = 0x02 } {
          context("EOR $C8FF,X") { addr = 0xc8ff.toShort; xr = 0x02 } {
            context("$C901 = $01") { setupOpArg(addr, xr, 0x01) } {
              it("meets preconditions") { assert(memoryRead(0xc901)() == 0x01) }
              it { expect2 { operation }.toChange { AC }.from(0x80).to(0x81) }
              it { expect2 { operation }.toUseCycles(0x05) }
            }
          }
        }
      }
    }
  }
}
