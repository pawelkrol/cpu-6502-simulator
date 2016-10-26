package com.github.pawelkrol.CPU6502
package Operations

class IndexSpec extends FunOperationsSpec {

  describe("implied addressing mode") {
    describe("decrement index Y by one") {
      testOpCode(OpCode_DEY, memSize = 1, cycles = 2) {
        context("YR = $00") { YR = 0x00 } {
          it { expect { operation }.toChange { YR }.to(0xff) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }

        context("YR = $01") { YR = 0x01 } {
          it { expect { operation }.toChange { YR }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("YR = $7F") { YR = 0x7f } {
          it { expect { operation }.toChange { YR }.to(0x7e) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("YR = $80") { YR = 0x80 } {
          it { expect { operation }.toChange { YR }.to(0x7f) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("YR = $ff") { YR = 0xff } {
          it { expect { operation }.toChange { YR }.to(0xfe) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }
      }
    }

    describe("decrement index X by one") {
      testOpCode(OpCode_DEX, memSize = 1, cycles = 2) {
        context("XR = $00") { XR = 0x00 } {
          it { expect { operation }.toChange { XR }.to(0xff) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }

        context("XR = $01") { XR = 0x01 } {
          it { expect { operation }.toChange { XR }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("XR = $7F") { XR = 0x7f } {
          it { expect { operation }.toChange { XR }.to(0x7e) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("XR = $80") { XR = 0x80 } {
          it { expect { operation }.toChange { XR }.to(0x7f) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("XR = $ff") { XR = 0xff } {
          it { expect { operation }.toChange { XR }.to(0xfe) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }
      }
    }

    describe("increment index Y by one") {
      testOpCode(OpCode_INY, memSize = 1, cycles = 2) {
        context("YR = $00") { YR = 0x00 } {
          it { expect { operation }.toChange { YR }.to(0x01) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("YR = $01") { YR = 0x01 } {
          it { expect { operation }.toChange { YR }.to(0x02) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("YR = $7F") { YR = 0x7f } {
          it { expect { operation }.toChange { YR }.to(0x80) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }

        context("YR = $80") { YR = 0x80 } {
          it { expect { operation }.toChange { YR }.to(0x81) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }

        context("YR = $ff") { YR = 0xff } {
          it { expect { operation }.toChange { YR }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }
      }
    }

    describe("increment index X by one") {
      testOpCode(OpCode_INX, memSize = 1, cycles = 2) {
        context("XR = $00") { XR = 0x00 } {
          it { expect { operation }.toChange { XR }.to(0x01) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("XR = $01") { XR = 0x01 } {
          it { expect { operation }.toChange { XR }.to(0x02) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(false) }
        }

        context("XR = $7F") { XR = 0x7f } {
          it { expect { operation }.toChange { XR }.to(0x80) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }

        context("XR = $80") { XR = 0x80 } {
          it { expect { operation }.toChange { XR }.to(0x81) }
          it { expect { operation }.toChange { ZF }.to(false) }
          it { expect { operation }.toChange { SF }.to(true) }
        }

        context("XR = $ff") { XR = 0xff } {
          it { expect { operation }.toChange { XR }.to(0x00) }
          it { expect { operation }.toChange { ZF }.to(true) }
          it { expect { operation }.toChange { SF }.to(false) }
        }
      }
    }
  }
}
