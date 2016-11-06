package com.github.pawelkrol.CPU6502
package Operations

class CompareIndexYSpec extends CompareSpec {

  protected val cycleCount = Map[OpCode, Int](
    OpCode_CPY_IMM -> 2,
    OpCode_CPY_ZP -> 3,
    OpCode_CPY_ABS -> 4
  )

  protected def comparedSymbol = "YR"

  protected def opCodeSymbol = "CPY"

  protected def assignLoadedValue(loadedValue: ByteVal) {
    YR = loadedValue
  }

  protected def fetchLoadedValue = () => YR

  describe("compare memory and index Y") {
    cycleCount.keys.foreach((opCode) => applySharedExamples(opCodeSymbol, opCode))
  }
}
