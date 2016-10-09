package com.github.pawelkrol.CPU6502
package Operations

class ImmediateSpec extends FunOperationsSpec {

  describe("immediate addressing mode") {
    testOpCode(OpCode_ORA_IMM) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $00") { AC = 0x00; ZF = true; SF = false } {
        context("ORA #$00") { assignOpArg(0x00) } {
          it { expect { operation }.notToChange { AC } }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }

        context("ORA #$01") { assignOpArg(0x01) } {
          it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
          it { expect { operation }.toChange { ZF }.from(true).to(false) }
          it { expect { operation }.notToChange { SF } }
        }

        context("ORA #$80") { assignOpArg(0x80) } {
          it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
          it { expect { operation }.toChange { ZF }.from(true).to(false) }
          it { expect { operation }.toChange { SF }.from(false).to(true) }
        }

        context("ORA #$ff") { assignOpArg(0xff) } {
          it { expect { operation }.toChange { AC }.from(0x00).to(0xff) }
          it { expect { operation }.toChange { ZF }.from(true).to(false) }
          it { expect { operation }.toChange { SF }.from(false).to(true) }
        }
      }
    }

    testOpCode(OpCode_AND_IMM) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $ff") { AC = 0xff; ZF = false; SF = true } {
        context("AND #$00") { assignOpArg(0x00) } {
          it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
          it { expect { operation }.toChange { ZF }.from(false).to(true) }
          it { expect { operation }.toChange { SF }.from(true).to(false) }
        }

        context("AND #$01") { assignOpArg(0x01) } {
          it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.toChange { SF }.from(true).to(false) }
        }

        context("AND #$80") { assignOpArg(0x80) } {
          it { expect { operation }.toChange { AC }.from(0xff).to(0x80) }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }

        context("AND #$ff") { assignOpArg(0xff) } {
          it { expect { operation }.notToChange { AC } }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }
      }
    }

    testOpCode(OpCode_EOR_IMM) {
      it { expect { operation }.toAdvancePC(0x02) }

      context("AC = $80") { AC = 0x80; ZF = false; SF = true } {
        context("EOR #$00") { assignOpArg(0x00) } {
          it { expect { operation }.notToChange { AC } }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }

        context("EOR #$01") { assignOpArg(0x01) } {
          it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }

        context("EOR #$80") { assignOpArg(0x80) } {
          it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
          it { expect { operation }.toChange { ZF }.from(false).to(true) }
          it { expect { operation }.toChange { SF }.from(true).to(false) }
        }

        context("EOR #$ff") { assignOpArg(0xff) } {
          it { expect { operation }.toChange { AC }.from(0x80).to(0x7f) }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.toChange { SF }.from(true).to(false) }
        }
      }
    }
  }
}