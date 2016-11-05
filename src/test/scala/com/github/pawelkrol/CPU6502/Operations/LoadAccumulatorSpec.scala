package com.github.pawelkrol.CPU6502
package Operations

class LoadAccumulatorSpec extends LoadSpec {

  protected val cycleCount = Map[OpCode, Int](
    OpCode_LDA_IMM -> 2,
    OpCode_LDA_ZP -> 3,
    OpCode_LDA_ZPX -> 4,
    OpCode_LDA_ABS -> 4,
    OpCode_LDA_ABSX -> 4,
    OpCode_LDA_ABSY -> 4,
    OpCode_LDA_INDX -> 6,
    OpCode_LDA_INDY -> 5
  )

  protected def setupSharedExamples {
    sharedExamples("LDA", (args) => {
      val expectedValue: Int = args(0).asInstanceOf[Int]

      it("AC = $%02X".format(expectedValue)) { expect { operation }.toChange { AC() }.from(0x00).to(expectedValue) }

      assertFlags(expectedValue)
    })
  }

  describe("load accumulator with memory") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("LDA", opCode))

    describe("absolute,x addressing mode") {
      assertPageBoundaryCycleCount(OpCode_LDA_ABSX) { (address, offset, assertionCallback) =>
        context("XR = $%02X".format(offset)) { XR = offset } {
          context("LDA $%04X,X".format(address)) { setupAbsIndexedOpArg(address.toShort, offset, 0xff) } {
            assertionCallback()
          }
        }
      }
    }

    describe("absolute,y addressing mode") {
      assertPageBoundaryCycleCount(OpCode_LDA_ABSY) { (address, offset, assertionCallback) =>
        context("YR = $%02X".format(offset)) { YR = offset } {
          context("LDA $%04X,Y".format(address)) { setupAbsIndexedOpArg(address.toShort, offset, 0xff) } {
            assertionCallback()
          }
        }
      }
    }

    describe("(indirect),y addressing mode") {
      assertPageBoundaryCycleCount(OpCode_LDA_INDY) { (address, offset, assertionCallback) =>
        context("YR = $%02X".format(offset)) { YR = offset } {
          context("LDA ($%02X),Y".format(address)) { setupIndirectOpArg(zp.toShort, offset, address.toShort, 0xff) } {
            assertionCallback()
          }
        }
      }
    }
  }
}
