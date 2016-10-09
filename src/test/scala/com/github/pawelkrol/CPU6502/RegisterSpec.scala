package com.github.pawelkrol.CPU6502

import Status._

class RegisterSpec extends FunFunSpec {

  private var register: Register = _

  describe("getStatusFlag") {
    context("SR = $00") { register = Register(SR = 0x00) } {
      it { expect { register.getStatusFlag(CF) }.toEqual(false) }
      it { expect { register.getStatusFlag(SF) }.toEqual(false) }
    }

    context("SR = $df") { register = Register(SR = 0xdf) } {
      it { expect { register.getStatusFlag(CF) }.toEqual(true) }
      it { expect { register.getStatusFlag(CF) }.toEqual(true) }
    }
  }

  describe("setStatusFlag") {
    describe("set the flag") {
      before { register = Register(SR = 0x00) }

      it("sets carry flag") {
        expect { register.setStatusFlag(CF, true) }.toChange { register.getStatusFlag(CF) }.from(false).to(true)
      }

      it("sets sign flag") {
        expect { register.setStatusFlag(SF, true) }.toChange { register.getStatusFlag(SF) }.from(false).to(true)
      }
    }

    describe("clear the flag") {
      before { register = Register(SR = 0xdf) }

      it("clears carry flag") {
        expect { register.setStatusFlag(CF, false) }.toChange { register.getStatusFlag(CF) }.from(true).to(false)
      }

      it("clears sign flag") {
        expect { register.setStatusFlag(SF, false) }.toChange { register.getStatusFlag(SF) }.from(true).to(false)
      }
    }
  }

  private var value: Short = _

  private def testTestStatusFlag(flags: Tuple2[Flag, Boolean]*) {
    flags.foreach({ case (flag, expectation) =>
      it { expect { register.testStatusFlag(flag, value); register.getStatusFlag(flag) }.toEqual(expectation) }
    })
  }

  describe("testStatusFlag") {
    context("test argument value $00") { value = 0x00 } { testTestStatusFlag(ZF -> true, SF -> false, CF -> false) }
    context("test argument value $01") { value = 0x01 } { testTestStatusFlag(ZF -> false, SF -> false, CF -> false) }
    context("test argument value $7f") { value = 0x7f } { testTestStatusFlag(ZF -> false, SF -> false, CF -> false) }
    context("test argument value $80") { value = 0x80 } { testTestStatusFlag(ZF -> false, SF -> true, CF -> false) }
    context("test argument value $81") { value = 0x81 } { testTestStatusFlag(ZF -> false, SF -> true, CF -> false) }
    context("test argument value $ff") { value = 0xff } { testTestStatusFlag(ZF -> false, SF -> true, CF -> false) }
    context("test argument value $100") { value = 0x100 } { testTestStatusFlag(ZF -> true, SF -> false, CF -> true) }
  }

  describe("statusFlags") {
    context("SR = $00") { register = Register(SR = 0x00) } {
      it("..-.....") { assert(register.statusFlags === "..-.....") }
    }

    context("SR = $FF") { register = Register(SR = 0xff) } {
      it("NV-BDIZC") { assert(register.statusFlags === "NV-BDIZC") }
    }
  }
}
