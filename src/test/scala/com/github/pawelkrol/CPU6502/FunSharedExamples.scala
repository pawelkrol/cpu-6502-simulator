package com.github.pawelkrol.CPU6502
package Operations

trait FunSharedExamples extends FunOperationsSpec {

  protected def setupSharedExamples: Unit

  setupSharedExamples

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit): Unit

  protected var zp: ByteVal = _

  protected var xr: ByteVal = _

  protected var addr: Short = _

  protected var includeSharedExamples: () => Unit = _

  protected def setupAbsOpArg(address: Short, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ value): _*) }

  protected def setupAbsXOpArg(address: Short, xr: ByteVal, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ xr :+ value): _*) }

  protected def sharedExampleArguments(opCode: OpCode): () => List[Any]

  def applySharedExamples(sym: String, op: OpCode) {
    includeSharedExamples = () => includeExamples(sym, sharedExampleArguments(op)())

    op match {
      case _: OpCode_AC => {
        describe("accumulator addressing mode") {
          testOpCode(op) {
            it("advances PC by 1 byte") { expect { operation }.toAdvancePC(0x01) }
            it("uses 2 CPU cycles") { expect { operation }.toUseCycles(0x02) }

            context(sym + " A") { assignOpArg() } {
              executeSharedExamples("AC", (opArg) => { AC = opArg })
            }
          }
        }
      }

      case _: OpCode_ZP => {
        describe("zeropage addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            it("uses 5 CPU cycles") { expect { operation }.toUseCycles(0x05) }

            context(sym + " $02") { zp = 0x02 } {
              executeSharedExamples("$0002", (opArg) => { assignOpArg(zp, opArg) })
            }
          }
        }
      }

      case _: OpCode_ZPX => {
        describe("zeropage,x addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            it("uses 6 CPU cycles") { expect { operation }.toUseCycles(0x06) }

            context("XR = $02") { XR = 0x02 } {
              context(sym + " $02,X") { zp = 0x02; xr = 0x02 } {
                executeSharedExamples("$0004", (opArg) => { assignOpArg(zp, xr, opArg) })
              }
            }
          }
        }
      }

      case _: OpCode_ABS => {
        describe("absolute addressing mode") {
          testOpCode(op) {
            it("advances PC by 3 bytes") { expect { operation }.toAdvancePC(0x03) }
            it("uses 6 CPU cycles") { expect { operation }.toUseCycles(0x06) }

            context(sym + " $C800") { addr = 0xc800.toShort } {
              executeSharedExamples("$C800", (opArg) => { setupAbsOpArg(addr, opArg) })
            }
          }
        }
      }

      case _: OpCode_ABSX => {
        describe("absolute,x addressing mode") {
          testOpCode(op) {
            it("advances PC by 3 bytes") { expect { operation }.toAdvancePC(0x03) }
            it("uses 7 CPU cycles") { expect { operation }.toUseCycles(0x07) }

            context("XR = $02") { XR = 0x02 } {
              context(sym + " $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
                executeSharedExamples("$C802", (opArg) => { setupAbsXOpArg(addr, xr, opArg) })
              }
            }
          }
        }
      }
    }
  }
}
