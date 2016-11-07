package com.github.pawelkrol.CPU6502
package Operations

trait FunSharedExamples extends FunOperationsSpec {

  protected def setupSharedExamples: Unit

  setupSharedExamples

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit): Unit

  protected var xr: ByteVal = _

  protected var yr: ByteVal = _

  protected var zpAddr: Short = _

  protected var addr: Short = _

  protected var includeSharedExamples: () => Unit = _

  protected def sharedExampleArguments(opCode: OpCode): () => List[Any]

  def applySharedExamples(sym: String, op: OpCode) {
    includeSharedExamples = () => includeExamples(sym, sharedExampleArguments(op)())

    op match {
      case _: OpCode_AC => {
        describe("accumulator addressing mode") {
          testOpCode(op) {
            it("advances PC by 1 byte") { expect { operation }.toAdvancePC(0x01) }
            assertCycleCount(cycleCount(op))

            context(sym + " A") { assignOpArg() } {
              executeSharedExamples("AC", (opArg) => { AC = opArg })
            }
          }
        }
      }

      case _: OpCode_IMM => {
        describe("immediate addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            assertCycleCount(cycleCount(op))

            context(sym + " #$XX") {} {
              executeSharedExamples("$C001", (opArg) => { assignOpArg(opArg) })
            }
          }
        }
      }

      case _: OpCode_ZP => {
        describe("zeropage addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            assertCycleCount(cycleCount(op))

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
            assertCycleCount(cycleCount(op))

            context("XR = $02") { XR = 0x02 } {
              context(sym + " $02,X") { zp = 0x02; xr = 0x02 } {
                executeSharedExamples("$0004", (opArg) => { assignOpArg(zp, xr, opArg) })
              }
            }
          }
        }
      }

      case _: OpCode_ZPY => {
        describe("zeropage,y addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            assertCycleCount(cycleCount(op))

            context("YR = $02") { YR = 0x02 } {
              context(sym + " $02,Y") { zp = 0x02; yr = 0x02 } {
                executeSharedExamples("$0004", (opArg) => { assignOpArg(zp, yr, opArg) })
              }
            }
          }
        }
      }

      case _: OpCode_ABS => {
        describe("absolute addressing mode") {
          testOpCode(op) {
            it("advances PC by 3 bytes") { expect { operation }.toAdvancePC(0x03) }
            assertCycleCount(cycleCount(op))

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
            assertCycleCount(cycleCount(op))

            context("XR = $02") { XR = 0x02 } {
              context(sym + " $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
                executeSharedExamples("$C802", (opArg) => { setupAbsIndexedOpArg(addr, xr, opArg) })
              }
            }
          }
        }
      }

      case _: OpCode_ABSY => {
        describe("absolute,y addressing mode") {
          testOpCode(op) {
            it("advances PC by 3 bytes") { expect { operation }.toAdvancePC(0x03) }
            assertCycleCount(cycleCount(op))

            context("YR = $02") { YR = 0x02 } {
              context(sym + " $C800,Y") { addr = 0xc800.toShort; yr = 0x02 } {
                executeSharedExamples("$C802", (opArg) => { setupAbsIndexedOpArg(addr, yr, opArg) })
              }
            }
          }
        }
      }

      case _: OpCode_INDX => {
        describe("(indirect,x) addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            assertCycleCount(cycleCount(op))

            context("XR = $02") { XR = 0x02 } {
              context(sym + " ($02,X)") { zp = 0x02; xr = 0x02 } {
                context("$0004 = $00, $0005 = $C8") { zpAddr = 0xc800.toShort } {
                  executeSharedExamples("$C800", (opArg) => { setupIndirectOpArg(zp, xr, zpAddr, opArg) })
                }
              }
            }
          }
        }
      }

      case _: OpCode_INDY => {
        describe("(indirect),y addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            assertCycleCount(cycleCount(op))

            context("YR = $02") { YR = 0x02 } {
              context(sym + " ($02),Y") { zp = 0x02; yr = 0x02 } {
                context("$0002 = $00, $0003 = $C8") { zpAddr = 0xc800.toShort } {
                  executeSharedExamples("$C802", (opArg) => { setupIndirectOpArg(zp, yr, zpAddr, opArg) })
                }
              }
            }
          }
        }
      }
    }
  }
}
