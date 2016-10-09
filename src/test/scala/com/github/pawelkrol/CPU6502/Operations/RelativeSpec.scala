package com.github.pawelkrol.CPU6502
package Operations

class RelativeSpec extends FunOperationsSpec {

  def shared_examples(branch: Boolean) {
    if (branch) {
      context("BRA *-$7E") { assignOpArg(0x80) } {
        it { expect { operation }.toAdvancePC(-0x7e) }
        it { expect { operation }.toUseCycles(0x04) }
      }

      context("BRA *-$01") { assignOpArg(0xfd) } {
        it { expect { operation }.toAdvancePC(-0x01) }
        it { expect { operation }.toUseCycles(0x04) }
      }

      context("BRA *+$00") { assignOpArg(0xfe) } {
        it { expect { operation }.toAdvancePC(0x00) }
        it { expect { operation }.toUseCycles(0x03) }
      }

      context("BRA *+$01") { assignOpArg(0xff) } {
        it { expect { operation }.toAdvancePC(0x01) }
        it { expect { operation }.toUseCycles(0x03) }
      }

      context("BRA *+$02") { assignOpArg(0x00) } {
        it { expect { operation }.toAdvancePC(0x02) }
        it { expect { operation }.toUseCycles(0x03) }
      }

      context("BRA *+$03") { assignOpArg(0x01) } {
        it { expect { operation }.toAdvancePC(0x03) }
        it { expect { operation }.toUseCycles(0x03) }
      }

      context("BRA *+$81") { assignOpArg(0x7f) } {
        it { expect { operation }.toAdvancePC(0x81) }
        it { expect { operation }.toUseCycles(0x03) }
      }
    }
    else {
      it { expect { operation }.toAdvancePC(0x02) }
      it { expect { operation }.toUseCycles(0x02) }
    }
  }

  describe("relative addressing mode") {
    describe("branch on carry clear") {
      testOpCode(OpCode_BCC_REL) {
        context("without carry flag") { CF = false } {
          shared_examples(true)
        }

        context("with carry flag") { CF = true } {
          shared_examples(false)
        }
      }
    }

    describe("branch on carry set") {
      testOpCode(OpCode_BCS_REL) {
        context("without carry flag") { CF = false } {
          shared_examples(false)
        }

        context("with carry flag") { CF = true } {
          shared_examples(true)
        }
      }
    }

    describe("branch on result zero") {
      testOpCode(OpCode_BEQ_REL) {
        context("without zero flag") { ZF = false } {
          shared_examples(false)
        }

        context("with zero flag") { ZF = true } {
          shared_examples(true)
        }
      }
    }

    describe("branch on result not zero") {
      testOpCode(OpCode_BNE_REL) {
        context("without zero flag") { ZF = false } {
          shared_examples(true)
        }

        context("with zero flag") { ZF = true } {
          shared_examples(false)
        }
      }
    }

    describe("branch on result minus") {
      testOpCode(OpCode_BMI_REL) {
        context("without sign flag") { SF = false } {
          shared_examples(false)
        }

        context("with sign flag") { SF = true } {
          shared_examples(true)
        }
      }
    }

    describe("branch on result plus") {
      testOpCode(OpCode_BPL_REL) {
        context("without sign flag") { SF = false } {
          shared_examples(true)
        }

        context("with sign flag") { SF = true } {
          shared_examples(false)
        }
      }
    }

    describe("branch on overflow clear") {
      testOpCode(OpCode_BVC_REL) {
        context("without overflow flag") { OF = false } {
          shared_examples(true)
        }

        context("with overflow flag") { OF = true } {
          shared_examples(false)
        }
      }
    }

    describe("branch on overflow set") {
      testOpCode(OpCode_BVS_REL) {
        context("without overflow flag") { OF = false } {
          shared_examples(false)
        }

        context("with overflow flag") { OF = true } {
          shared_examples(true)
        }
      }
    }
  }
}
