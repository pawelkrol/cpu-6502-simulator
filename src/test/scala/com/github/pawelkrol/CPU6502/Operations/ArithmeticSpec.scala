package com.github.pawelkrol.CPU6502
package Operations

trait ArithmeticSpec extends FunOperationsSpec {

  protected def setupSharedExamples: Unit

  setupSharedExamples

  protected var xr: ByteVal = _

  protected var yr: ByteVal = _

  protected var zpAddr: Short = _

  protected var addr: Short = _

  protected var includeSharedExamples: () => Unit = _

  private def executeSharedExamples(target: String, initTestCase: (Int) => Unit): Unit = {
    val decimalFlags = Map(true -> "with decimal flag", false -> "without decimal flag")
    decimalFlags.foreach { case (decimalFlag, decimalContext) => {
      val testedAccumulatorValues = decimalFlag match {
        case true =>
          List[ByteVal](0x00, 0x01, 0x09, 0x10, 0x99)
        case false =>
          List[ByteVal](0x00, 0x01, 0x7f, 0x80, 0xff)
      }
      context(decimalContext) { DF = decimalFlag } {
        testedAccumulatorValues.foreach(ac => {
          context("AC = $%02x".format(ac())) { AC = ac } {
            executeSharedExamplesWithCarryOptions(decimalFlag, target, initTestCase)
          }
        })
      }
    }}
  }

  private def executeSharedExamplesWithCarryOptions(decimal: Boolean, target: String, initTestCase: (Int) => Unit): Unit = {
    decimal match {
      case true => {
        context("with carry flag") { CF = true } {
          executeSharedExamplesWithDecimalFlag(target, initTestCase)
        }

        context("without carry flag") { CF = false } {
          executeSharedExamplesWithDecimalFlag(target, initTestCase)
        }
      }
      case false => {
        context("with carry flag") { CF = true } {
          executeSharedExamplesWithoutDecimalFlag(target, initTestCase)
        }

        context("without carry flag") { CF = false } {
          executeSharedExamplesWithoutDecimalFlag(target, initTestCase)
        }
      }
    }
  }

  private def executeSharedExamplesWithDecimalFlag(target: String, initTestCase: (Int) => Unit): Unit = {
    context(target + " = $00") { initTestCase(0x00) } { includeSharedExamples() }
    context(target + " = $01") { initTestCase(0x01) } { includeSharedExamples() }
    context(target + " = $09") { initTestCase(0x09) } { includeSharedExamples() }
    context(target + " = $10") { initTestCase(0x10) } { includeSharedExamples() }
    context(target + " = $99") { initTestCase(0x99) } { includeSharedExamples() }
  }

  private def executeSharedExamplesWithoutDecimalFlag(target: String, initTestCase: (Int) => Unit): Unit = {
    context(target + " = $00") { initTestCase(0x00) } { includeSharedExamples() }
    context(target + " = $01") { initTestCase(0x01) } { includeSharedExamples() }
    context(target + " = $7f") { initTestCase(0x7f) } { includeSharedExamples() }
    context(target + " = $80") { initTestCase(0x80) } { includeSharedExamples() }
    context(target + " = $ff") { initTestCase(0xff) } { includeSharedExamples() }
  }

  def applySharedExamples(sym: String, op: OpCode): Unit = {
    op match {
      case _: OpCode_IMM => {
        describe("immediate addressing mode") {
          testOpCode(op, memSize = 0x02, cycles = 0x02) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(0xc001)()))

            context("$C000 = " + sym + " #$XX") {} {
              executeSharedExamples("operation argument value at $C001", (opArg) => { assignOpArg(opArg) })
            }
          }
        }
      }
      case _: OpCode_ZP => {
        describe("zeropage addressing mode") {
          testOpCode(op, memSize = 0x02, cycles = 0x03) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(zp)()))

            context("$C000 = " + sym + " $02") { zp = 0x02 } {
              executeSharedExamples("operation argument value at $02", (opArg) => { assignOpArg(zp, opArg) })
            }
          }
        }
      }
      case _: OpCode_ZPX => {
        describe("zeropage,x addressing mode") {
          testOpCode(op, memSize = 0x02, cycles = 0x04) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(zp + xr)()))

            context("XR = $02") { XR = 0x02 } {
              context("$C000 = " + sym + " $02,X") { zp = 0x02; xr = 0x02 } {
                executeSharedExamples("operation argument value at $04", (opArg) => { assignOpArg(zp, xr, opArg) })
              }
            }
          }
        }
      }
      case _: OpCode_ABS => {
        describe("absolute addressing mode") {
          testOpCode(op, memSize = 0x03, cycles = 0x04) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(addr)()))

            context("$C000 = " + sym + " $C800") { addr = 0xc800.toShort } {
              executeSharedExamples("operation argument value at $C800", (opArg) => { setupAbsOpArg(addr, opArg) })
            }
          }
        }
      }
      case _: OpCode_ABSX => {
        describe("absolute,x addressing mode") {
          testOpCode(op, memSize = 0x03, cycles = 0x04) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(addr + xr)()))

            context("XR = $02") { XR = 0x02 } {
              context("$C000 = " + sym + " $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
                executeSharedExamples("operation argument value at $C802", (opArg) => { setupAbsIndexedOpArg(addr, xr, opArg) })
              }
            }
          }
        }
      }
      case _: OpCode_ABSY => {
        describe("absolute,y addressing mode") {
          testOpCode(op, memSize = 0x03, cycles = 0x04) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(addr + yr)()))

            context("YR = $02") { YR = 0x02 } {
              context("$C000 = " + sym + " $C800,Y") { addr = 0xc800.toShort; yr = 0x02 } {
                executeSharedExamples("operation argument value at $C802", (opArg) => { setupAbsIndexedOpArg(addr, yr, opArg) })
              }
            }
          }
        }
      }
      case _: OpCode_INDX => {
        describe("(indirect,x) addressing mode") {
          testOpCode(op, memSize = 0x02, cycles = 0x06) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(Util.nibbles2Word(memoryRead(zp + xr)(), memoryRead(zp + xr + 1)()))()))

            context("XR = $02") { XR = 0x02 } {
              context("$C000 = " + sym + " ($02,X)") { zp = 0x02; xr = 0x02 } {
                context("$0004 = $00, $0005 = $C8") { zpAddr = 0xc800.toShort } {
                  executeSharedExamples("operation argument value at $C800", (opArg) => { setupIndirectOpArg(zp, xr, zpAddr, opArg) })
                }
              }
            }
          }
        }
      }
      case _: OpCode_INDY => {
        describe("(indirect),y addressing mode") {
          testOpCode(op, memSize = 0x02, cycles = 0x05) {
            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(Util.nibbles2Word(memoryRead(zp)(), memoryRead(zp + 1)()) + yr)()))

            context("YR = $02") { YR = 0x02 } {
              context("$C000 = " + sym + " ($02),Y") { zp = 0x02; yr = 0x02 } {
                context("$0002 = $00, $0003 = $C8") { zpAddr = 0xc800.toShort } {
                  executeSharedExamples("operation argument value at $C802", (opArg) => { setupIndirectOpArg(zp, yr, zpAddr, opArg) })
                }
              }
            }
          }
        }
      }
    }
  }
}
