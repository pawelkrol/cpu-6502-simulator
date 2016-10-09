package com.github.pawelkrol.CPU6502
package Operations

class TransferSpec extends FunOperationsSpec {

  describe("implied addressing mode") {
    describe("transfer accumulator to index X") {
      testOpCode(OpCode_TAX, memSize = 1, cycles = 2) {
        context("AC = $00") { AC = 0x00 } {
          it { expect { operation }.toChange { XR }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("AC = $01") { AC = 0x01 } {
          it { expect { operation }.toChange { XR }.to(0x01) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("AC = $80") { AC = 0x80 } {
          it { expect { operation }.toChange { XR }.to(0x80) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }
      }
    }

    describe("transfer index X to accumulator") {
      testOpCode(OpCode_TXA, memSize = 1, cycles = 2) {
        context("XR = $00") { XR = 0x00 } {
          it { expect { operation }.toChange { AC }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("XR = $01") { XR = 0x01 } {
          it { expect { operation }.toChange { AC }.to(0x01) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("XR = $80") { XR = 0x80 } {
          it { expect { operation }.toChange { AC }.to(0x80) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }
      }
    }

    describe("transfer accumulator to index Y") {
      testOpCode(OpCode_TAY, memSize = 1, cycles = 2) {
        context("AC = $00") { AC = 0x00 } {
          it { expect { operation }.toChange { YR }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("AC = $01") { AC = 0x01 } {
          it { expect { operation }.toChange { YR }.to(0x01) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("AC = $80") { AC = 0x80 } {
          it { expect { operation }.toChange { YR }.to(0x80) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }
      }
    }

    describe("transfer index Y to accumulator") {
      testOpCode(OpCode_TYA, memSize = 1, cycles = 2) {
        context("YR = $00") { YR = 0x00 } {
          it { expect { operation }.toChange { AC }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("YR = $01") { YR = 0x01 } {
          it { expect { operation }.toChange { AC }.to(0x01) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("YR = $80") { YR = 0x80 } {
          it { expect { operation }.toChange { AC }.to(0x80) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }
      }
    }

    describe("transfer stack pointer to index X") {
      testOpCode(OpCode_TSX, memSize = 1, cycles = 2) {
        context("SP = $00") { SP = 0x00 } {
          it { expect { operation }.toChange { XR }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("SP = $01") { SP = 0x01 } {
          it { expect { operation }.toChange { XR }.to(0x01) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("SP = $80") { SP = 0x80 } {
          it { expect { operation }.toChange { XR }.to(0x80) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }
      }
    }

    describe("transfer index X to stack pointer") {
      testOpCode(OpCode_TXS, memSize = 1, cycles = 2) {
        context("XR = $00") { XR = 0x00 } {
          it { expect { operation }.toChange { SP }.to(0x00) }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }

        context("XR = $01") { XR = 0x01 } {
          it { expect { operation }.toChange { SP }.to(0x01) }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }

        context("XR = $80") { XR = 0x80 } {
          it { expect { operation }.toChange { SP }.to(0x80) }
          it { expect { operation }.notToChange { ZF } }
          it { expect { operation }.notToChange { SF } }
        }
      }
    }
  }
}
