package com.github.pawelkrol.CPU6502

import org.scalatest.funspec.AnyFunSpec

import java.io.File

class ArgumentsSpec extends AnyFunSpec {

  describe("validate") {
    it("fails to validate missing file name") {
      intercept[IllegalArgumentException] {
        Arguments().validate
      }
    }

    it("fails to validate non-existing file name") {
      intercept[IllegalArgumentException] {
        Arguments(file = Some(new File("main.prg"))).validate
      }
    }

    it("fails to validate negative start address") {
      intercept[IllegalArgumentException] {
        Arguments(startAddress = Some(-1)).validate
      }
    }

    it("fails to validate start address outside of an available memory range") {
      intercept[IllegalArgumentException] {
        Arguments(startAddress = Some(0x10000)).validate
      }
    }
  }
}
