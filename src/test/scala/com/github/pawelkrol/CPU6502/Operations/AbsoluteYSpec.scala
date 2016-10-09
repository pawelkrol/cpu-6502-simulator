package com.github.pawelkrol.CPU6502
package Operations

class AbsoluteYSpec extends FunOperationsSpec {

  private var addr: Short = _

  private var yr: ByteVal = _

  private def setupOpArg(address: Short, yr: ByteVal, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ yr :+ value): _*) }

  describe("absolute,x addressing mode") {
    testOpCode(OpCode_ORA_ABSY) {
      it { expect { operation }.toAdvancePC(0x03) }
      it { expect { operation }.toUseCycles(0x04) }

      context("AC = $00; YR = $02") { AC = 0x00; YR = 0x02; ZF = true; SF = false } {
        context("ORA $C800,Y") { addr = 0xc800.toShort; yr = 0x02 } {
          context("$C802 = $00") { setupOpArg(addr, yr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $01") { setupOpArg(addr, yr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $80") { setupOpArg(addr, yr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }

          context("$C802 = $ff") { setupOpArg(addr, yr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0xff) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }
        }
      }

      describe("with page cross") {
        context("AC = $00; YR = $02") { AC = 0x00; YR = 0x02 } {
          context("ORA $C8FF,Y") { addr = 0xc8ff.toShort; yr = 0x02 } {
            context("$C901 = $01") { setupOpArg(addr, yr, 0x01) } {
              it("meets preconditions") { assert(memoryRead(0xc901) == 0x01) }
              it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
              it { expect { operation }.toUseCycles(0x05) }
            }
          }
        }
      }
    }

    testOpCode(OpCode_AND_ABSY) {
      it { expect { operation }.toAdvancePC(0x03) }
      it { expect { operation }.toUseCycles(0x04) }

      context("AC = $ff; YR = $02") { AC = 0xff; YR = 0x02; ZF = false; SF = true } {
        context("AND $C800,Y") { addr = 0xc800.toShort; yr = 0x02 } {
          context("$C802 = $00") { setupOpArg(addr, yr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x00) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $01") { setupOpArg(addr, yr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $80") { setupOpArg(addr, yr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x80) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $ff") { setupOpArg(addr, yr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0xff) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }
        }
      }

      describe("with page cross") {
        context("AC = $ff; YR = $02") { AC = 0xff; YR = 0x02 } {
          context("AND $C8FF,Y") { addr = 0xc8ff.toShort; yr = 0x02 } {
            context("$C901 = $01") { setupOpArg(addr, yr, 0x01) } {
              it("meets preconditions") { assert(memoryRead(0xc901) == 0x01) }
              it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
              it { expect { operation }.toUseCycles(0x05) }
            }
          }
        }
      }
    }

    testOpCode(OpCode_EOR_ABSY) {
      it { expect { operation }.toAdvancePC(0x03) }
      it { expect { operation }.toUseCycles(0x04) }

      context("AC = $80; YR = $02") { AC = 0x80; YR = 0x02; ZF = false; SF = true } {
        context("EOR $C800,Y") { addr = 0xc800.toShort; yr = 0x02 } {
          context("$C802 = $00") { setupOpArg(addr, yr, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $01") { setupOpArg(addr, yr, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$C802 = $80") { setupOpArg(addr, yr, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$C802 = $ff") { setupOpArg(addr, yr, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0xc802) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x7f) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }

      describe("with page cross") {
        context("AC = $80; YR = $02") { AC = 0x80; YR = 0x02 } {
          context("EOR $C8FF,Y") { addr = 0xc8ff.toShort; yr = 0x02 } {
            context("$C901 = $01") { setupOpArg(addr, yr, 0x01) } {
              it("meets preconditions") { assert(memoryRead(0xc901) == 0x01) }
              it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
              it { expect { operation }.toUseCycles(0x05) }
            }
          }
        }
      }
    }
  }
}
