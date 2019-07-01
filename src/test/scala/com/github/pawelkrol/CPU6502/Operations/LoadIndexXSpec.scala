package com.github.pawelkrol.CPU6502
package Operations

class LoadIndexXSpec extends LoadSpec {

  cycleCount = Map[OpCode, Int](
    OpCode_LDX_IMM -> 2,
    OpCode_LDX_ZP -> 3,
    OpCode_LDX_ZPY -> 4,
    OpCode_LDX_ABS -> 4,
    OpCode_LDX_ABSY -> 4
  )

  protected def setupSharedExamples: Unit = {
    sharedExamples("LDX", (args) => {
      val expectedValue: Int = args(0).asInstanceOf[Int]

      it("XR = $%02X".format(expectedValue)) { expect { operation }.toChange { XR() }.from(0x00).to(expectedValue) }

      assertFlags(expectedValue)
    })
  }

  describe("load index X with memory") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("LDX", opCode))

    describe("absolute,y addressing mode") {
      assertPageBoundaryCycleCount(OpCode_LDX_ABSY) { (address, offset, assertionCallback) =>
        context("YR = $%02X".format(offset)) { YR = offset } {
          context("LDX $%04X,Y".format(address)) { setupAbsIndexedOpArg(address.toShort, offset, 0xff) } {
            assertionCallback()
          }
        }
      }
    }
  }
}
