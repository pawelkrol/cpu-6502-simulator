package com.github.pawelkrol.CPU6502
package Operations

class StoreAccumulatorSpec extends StoreSpec {

  protected val storedSymbol = "AC"

  protected def assignStoredValue(storedValue: ByteVal) {
    AC = storedValue
  }

  cycleCount = Map[OpCode, Int](
    OpCode_STA_ZP -> 3,
    OpCode_STA_ZPX -> 4,
    OpCode_STA_ABS -> 4,
    OpCode_STA_ABSX -> 5,
    OpCode_STA_ABSY -> 5,
    OpCode_STA_INDX -> 6,
    OpCode_STA_INDY -> 6
  )

  protected def setupSharedExamples {
    sharedExamples("STA", (args) => {
      val fetchAddress: () => Int = args(0).asInstanceOf[() => Int]
      val fetchValue: () => Int = args(1).asInstanceOf[() => Int]

      val message = "$%04X = $%02X".format(fetchAddress().toShort, AC())

      it(message) { expect { operation }.toChange { fetchValue() }.from(0xff).to(AC()) }
    })
  }

  describe("store accumulator in memory") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("STA", opCode))
  }
}
