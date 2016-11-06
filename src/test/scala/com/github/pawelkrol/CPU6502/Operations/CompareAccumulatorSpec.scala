package com.github.pawelkrol.CPU6502
package Operations

class CompareAccumulatorSpec extends CompareSpec {

  protected val cycleCount = Map[OpCode, Int](
    OpCode_CMP_IMM -> 2,
    OpCode_CMP_ZP -> 3,
    OpCode_CMP_ZPX -> 4,
    OpCode_CMP_ABS -> 4,
    OpCode_CMP_ABSX -> 4,
    OpCode_CMP_ABSY -> 4,
    OpCode_CMP_INDX -> 6,
    OpCode_CMP_INDY -> 5
  )

  protected def comparedSymbol = "AC"

  protected def opCodeSymbol = "CMP"

  protected def assignLoadedValue(loadedValue: ByteVal) {
    AC = loadedValue
  }

  protected def fetchLoadedValue = () => AC

  describe("compare memory and accumulator") {
    cycleCount.keys.foreach((opCode) => applySharedExamples(opCodeSymbol, opCode))

    pageBoundaryCrossCheck(OpCode_CMP_ABSX, opCodeSymbol)
    pageBoundaryCrossCheck(OpCode_CMP_ABSY, opCodeSymbol)
    pageBoundaryCrossCheck(OpCode_CMP_INDY, opCodeSymbol)
  }
}
