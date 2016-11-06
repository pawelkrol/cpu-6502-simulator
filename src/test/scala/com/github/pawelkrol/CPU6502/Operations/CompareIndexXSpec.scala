package com.github.pawelkrol.CPU6502
package Operations

class CompareIndexXSpec extends CompareSpec {

  protected val cycleCount = Map[OpCode, Int](
    OpCode_CPX_IMM -> 2,
    OpCode_CPX_ZP -> 3,
    OpCode_CPX_ABS -> 4
  )

  protected def comparedSymbol = "XR"

  protected def opCodeSymbol = "CPX"

  protected def assignLoadedValue(loadedValue: ByteVal) {
    XR = loadedValue
  }

  protected def fetchLoadedValue = () => XR

  describe("compare memory and index X") {
    cycleCount.keys.foreach((opCode) => applySharedExamples(opCodeSymbol, opCode))
  }
}
