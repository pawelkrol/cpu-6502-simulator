package com.github.pawelkrol.CPU6502
package Operations

class AddWithCarrySpec extends FunOperationsSpec {

  describe("add memory to accumulator with carry") {
    describe("immediate addressing mode") {
      testOpCode(OpCode_ADC_IMM) {
      }
    }

    describe("zeropage addressing mode") {
      testOpCode(OpCode_ADC_ZP) {
      }
    }

    describe("zeropage,x addressing mode") {
      testOpCode(OpCode_ADC_ZPX) {
      }
    }

    describe("absolute addressing mode") {
      testOpCode(OpCode_ADC_ABS) {
      }
    }

    describe("absolute,x addressing mode") {
      testOpCode(OpCode_ADC_ABSX) {
      }
    }

    describe("absolute,y addressing mode") {
      testOpCode(OpCode_ADC_ABSY) {
      }
    }

    describe("(indirect,x) addressing mode") {
      testOpCode(OpCode_ADC_INDX) {
      }
    }

    describe("(indirect),y addressing mode") {
      testOpCode(OpCode_ADC_INDY) {
      }
    }
  }
}
