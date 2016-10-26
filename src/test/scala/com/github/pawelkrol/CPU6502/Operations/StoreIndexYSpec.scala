package com.github.pawelkrol.CPU6502
package Operations

class StoreIndexYSpec extends StoreSpec {

  protected val storedSymbol = "YR"

  protected def assignStoredValue(storedValue: ByteVal) {
    YR = storedValue
  }

  protected val cycleCount = Map[OpCode, Int](
    OpCode_STY_ZP -> 3,
    OpCode_STY_ZPX -> 4,
    OpCode_STY_ABS -> 4
  )

  protected def setupSharedExamples {
    sharedExamples("STY", (args) => {
      val fetchAddress: () => Int = args(0).asInstanceOf[() => Int]
      val fetchValue: () => Int = args(1).asInstanceOf[() => Int]

      val message = "$%04X = $%02X".format(fetchAddress().toShort, YR())

      it(message) { expect { operation }.toChange { fetchValue() }.from(0xff).to(YR()) }
    })
  }

  describe("store index Y in memory") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("STY", opCode))
  }
}
