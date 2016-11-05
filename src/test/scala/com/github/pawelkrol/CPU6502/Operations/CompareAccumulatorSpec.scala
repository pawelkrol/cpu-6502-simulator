package com.github.pawelkrol.CPU6502
package Operations

class CompareAccumulatorSpec extends  CompareSpec {

  protected val cycleCount = Map[OpCode, Int](
    OpCode_CMP_IMM -> 2
  )

  protected def comparedSymbol = "AC"

  protected def assignLoadedValue(loadedValue: ByteVal) {
    AC = loadedValue
  }

  protected def fetchLoadedValue = () => AC

  describe("compare memory and accumulator") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("compare", opCode))
  }

  describe("compare memory and accumulator (old)") {
    describe("zeropage addressing mode") {
      testOpCode(OpCode_CMP_ZP) {
        // TODO
      }
    }

    describe("zeropage,x addressing mode") {
      testOpCode(OpCode_CMP_ZPX) {
        // TODO
      }
    }

    describe("absolute addressing mode") {
      testOpCode(OpCode_CMP_ABS) {
        // TODO
      }
    }

    describe("absolute,x addressing mode") {
      testOpCode(OpCode_CMP_ABSX) {
        // TODO
      }
    }

    describe("absolute,y addressing mode") {
      testOpCode(OpCode_CMP_ABSY) {
        // TODO
      }
    }

    describe("(indirect,x) addressing mode") {
      testOpCode(OpCode_CMP_INDX) {
        // TODO
      }
    }

    describe("(indirect),y addressing mode") {
      testOpCode(OpCode_CMP_INDY) {
        // TODO
      }
    }
  }
}
