package com.github.pawelkrol.CPU6502
package Operations

class LoadAccumulatorSpec extends LoadSpec {

  cycleCount = Map[OpCode, Int](
    OpCode_LDA_IMM -> 2,
    OpCode_LDA_ZP -> 3,
    OpCode_LDA_ZPX -> 4,
    OpCode_LDA_ABS -> 4,
    OpCode_LDA_ABSX -> 4,
    OpCode_LDA_ABSY -> 4,
    OpCode_LDA_INDX -> 6,
    OpCode_LDA_INDY -> 5
  )

  protected def setupSharedExamples: Unit = {
    sharedExamples("LDA", (args) => {
      val expectedValue: Int = args(0).asInstanceOf[Int]

      it("AC = $%02X".format(expectedValue)) { expect { operation }.toChange { AC() }.from(0x00).to(expectedValue) }

      assertFlags(expectedValue)
    })
  }

  describe("load accumulator with memory") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("LDA", opCode))

    pageBoundaryCrossCheck(OpCode_LDA_ABSX, "LDA")
    pageBoundaryCrossCheck(OpCode_LDA_ABSY, "LDA")
    pageBoundaryCrossCheck(OpCode_LDA_INDY, "LDA")
  }
}
