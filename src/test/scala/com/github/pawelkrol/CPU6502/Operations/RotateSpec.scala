package com.github.pawelkrol.CPU6502
package Operations

trait RotateSpec extends FunOperationsSpec {

  def setupSharedExamples: Unit

  setupSharedExamples

  protected var zp: ByteVal = _

  protected var xr: ByteVal = _

  protected var addr: Short = _

  protected var includeSharedExamples: () => Unit = _

  protected def setupAbsOpArg(address: Short, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ value): _*) }

  protected def setupAbsXOpArg(address: Short, xr: ByteVal, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ xr :+ value): _*) }

  def executeSharedExamples(target: String, initTestCase: (Int) => Unit) {
    context("with carry flag") { CF = true } {
      executeSharedExamplesWithCarry(target, true, initTestCase)
    }

    context("without carry flag") { CF = false } {
      executeSharedExamplesWithCarry(target, false, initTestCase)
    }
  }

  def executeSharedExamplesWithCarry(target: String, carry: Boolean, initTestCase: (Int) => Unit) {
    context(target + " = $00") { initTestCase(0x00) } { includeSharedExamples() }
    context(target + " = $01") { initTestCase(0x01) } { includeSharedExamples() }
    context(target + " = $02") { initTestCase(0x02) } { includeSharedExamples() }
    context(target + " = $40") { initTestCase(0x40) } { includeSharedExamples() }
    context(target + " = $80") { initTestCase(0x80) } { includeSharedExamples() }
  }

  def applySharedExamples(sym: String, op: OpCode) {
    op match {
      case _: OpCode_AC => {
        describe("accumulator addressing mode") {
          testOpCode(op) {
            it { expect { operation }.toAdvancePC(0x01) }
            it { expect { operation }.toUseCycles(0x02) }

            includeSharedExamples = () => includeExamples(sym, List[Any](CF, () => AC()))

            context(sym + " A") { assignOpArg() } {
              executeSharedExamples("AC", (opArg) => { AC = opArg })
            }
          }
        }
      }

      case _: OpCode_ZP => {
        describe("zeropage addressing mode") {
          testOpCode(op) {
            it { expect { operation }.toAdvancePC(0x02) }
            it { expect { operation }.toUseCycles(0x05) }

            includeSharedExamples = () => includeExamples(sym, List[Any](CF, () => memoryRead(zp)()))

            context(sym + " $02") { zp = 0x02 } {
              executeSharedExamples("$0002", (opArg) => { assignOpArg(zp, opArg) })
            }
          }
        }
      }

      case _: OpCode_ZPX => {
        describe("zeropage,x addressing mode") {
          testOpCode(op) {
            it { expect { operation }.toAdvancePC(0x02) }
            it { expect { operation }.toUseCycles(0x06) }

            includeSharedExamples = () => includeExamples(sym, List[Any](CF, () => memoryRead(zp + xr)()))

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
            it { expect { operation }.toAdvancePC(0x03) }
            it { expect { operation }.toUseCycles(0x06) }

            includeSharedExamples = () => includeExamples(sym, List[Any](CF, () => memoryRead(addr)()))

            context(sym + " $C800") { addr = 0xc800.toShort } {
              executeSharedExamples("$C800", (opArg) => { setupAbsOpArg(addr, opArg) })
            }
          }
        }
      }

      case _: OpCode_ABSX => {
        describe("absolute,x addressing mode") {
          testOpCode(op) {
            it { expect { operation }.toAdvancePC(0x03) }
            it { expect { operation }.toUseCycles(0x07) }

            includeSharedExamples = () => includeExamples(sym, List[Any](CF, () => memoryRead(addr + xr)()))

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
