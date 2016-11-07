package com.github.pawelkrol.CPU6502
package Operations

class StoreIndexXSpec extends StoreSpec {

  protected val storedSymbol = "XR"

  protected def assignStoredValue(storedValue: ByteVal) {
    XR = storedValue
  }

  cycleCount = Map[OpCode, Int](
    OpCode_STX_ZP -> 3,
    OpCode_STX_ZPY -> 4,
    OpCode_STX_ABS -> 4
  )

  protected def setupSharedExamples {
    sharedExamples("STX", (args) => {
      val fetchAddress: () => Int = args(0).asInstanceOf[() => Int]
      val fetchValue: () => Int = args(1).asInstanceOf[() => Int]

      val message = "$%04X = $%02X".format(fetchAddress().toShort, XR())

      it(message) { expect { operation }.toChange { fetchValue() }.from(0xff).to(XR()) }
    })
  }

  describe("store index X in memory") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("STX", opCode))
  }
}
