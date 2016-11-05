package com.github.pawelkrol.CPU6502
package Operations

class LoadIndexYSpec extends LoadSpec {

  protected val cycleCount = Map[OpCode, Int](
    OpCode_LDY_IMM -> 2,
    OpCode_LDY_ZP -> 3,
    OpCode_LDY_ZPX -> 4,
    OpCode_LDY_ABS -> 4,
    OpCode_LDY_ABSX -> 4
  )

  protected def setupSharedExamples {
    sharedExamples("LDY", (args) => {
      val expectedValue: Int = args(0).asInstanceOf[Int]

      it("YR = $%02X".format(expectedValue)) { expect { operation }.toChange { YR() }.from(0x00).to(expectedValue) }

      assertFlags(expectedValue)
    })
  }

  describe("load index Y with memory") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("LDY", opCode))

    describe("absolute,x addressing mode") {
      assertPageBoundaryCycleCount(OpCode_LDY_ABSX) { (address, offset, assertionCallback) =>
        context("XR = $%02X".format(offset)) { XR = offset } {
          context("LDY $%04X,X".format(address)) { setupAbsIndexedOpArg(address.toShort, offset, 0xff) } {
            assertionCallback()
          }
        }
      }
    }
  }
}
