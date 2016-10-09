package com.github.pawelkrol.CPU6502
package Operations

class ZeroPageSpec extends FunOperationsSpec {

  describe("zeropage addressing mode") {
    testOpCode(OpCode_ORA_ZP, memSize = 0x02, cycles = 0x03) {
      context("AC = $00") { AC = 0x00; ZF = true; SF = false } {
        context("ORA $02") { zp = 0x02 } {
          context("$0002 = $00") { assignOpArg(zp, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$0002 = $01") { assignOpArg(zp, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.notToChange { SF } }
          }

          context("$0002 = $80") { assignOpArg(zp, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }

          context("$0002 = $ff") { assignOpArg(zp, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x00).to(0xff) }
            it { expect { operation }.toChange { ZF }.from(true).to(false) }
            it { expect { operation }.toChange { SF }.from(false).to(true) }
          }
        }
      }
    }

    testOpCode(OpCode_AND_ZP, memSize = 0x02, cycles = 0x03) {
      context("AC = $00") { AC = 0xff; ZF = false; SF = true } {
        context("AND $02") { zp = 0x02 } {
          context("$0002 = $00") { assignOpArg(zp, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x00) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0002 = $01") { assignOpArg(zp, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0002 = $80") { assignOpArg(zp, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0xff).to(0x80) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$0002 = $ff") { assignOpArg(zp, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0xff) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }
        }
      }
    }

    testOpCode(OpCode_EOR_ZP, memSize = 0x02, cycles = 0x03) {
      context("AC = $80") { AC = 0x80; ZF = false; SF = true } {
        context("EOR $02") { zp = 0x02 } {
          context("$0002 = $00") { assignOpArg(zp, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$0002 = $01") { assignOpArg(zp, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x01) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$0002 = $80") { assignOpArg(zp, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x80) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0002 = $ff") { assignOpArg(zp, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0xff) }
            it { expect { operation }.toChange { AC }.from(0x80).to(0x7f) }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }
        }
      }
    }

    testOpCode(OpCode_BIT_ZP, memSize = 0x02, cycles = 0x03) {
      context("AC = $80") { AC = 0x80; ZF = false; OF = false; SF = true } {
        context("BIT $02") { zp = 0x02 } {
          context("$0002 = $00") { assignOpArg(zp, 0x00) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x00) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.notToChange { OF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0002 = $01") { assignOpArg(zp, 0x01) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x01) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.notToChange { OF } }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0002 = $40") { assignOpArg(zp, 0x40) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x40) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.toChange { ZF }.from(false).to(true) }
            it { expect { operation }.toChange { OF }.from(false).to(true) }
            it { expect { operation }.toChange { SF }.from(true).to(false) }
          }

          context("$0002 = $80") { assignOpArg(zp, 0x80) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0x80) }
            it { expect { operation }.notToChange { AC } }
            it { expect { operation }.notToChange { ZF } }
            it { expect { operation }.notToChange { OF } }
            it { expect { operation }.notToChange { SF } }
          }

          context("$0002 = $ff") { assignOpArg(zp, 0xff) } {
            it("meets preconditions") { assert(memoryRead(0x0002) == 0xff) }
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
