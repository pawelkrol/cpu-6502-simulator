package com.github.pawelkrol.CPU6502
package Operations

trait ArithmeticSpec extends FunOperationsSpec {

  protected def setupSharedExamples: Unit

  setupSharedExamples

  protected var zp: ByteVal = _

  protected var includeSharedExamples: () => Unit = _

  private def executeSharedExamples(target: String, initTestCase: (Int) => Unit) {
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

  private def executeSharedExamplesWithCarryOptions(decimal: Boolean, target: String, initTestCase: (Int) => Unit) {
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

  private def executeSharedExamplesWithDecimalFlag(target: String, initTestCase: (Int) => Unit) {
    context(target + " = $00") { initTestCase(0x00) } { includeSharedExamples() }
    context(target + " = $01") { initTestCase(0x01) } { includeSharedExamples() }
    context(target + " = $09") { initTestCase(0x09) } { includeSharedExamples() }
    context(target + " = $10") { initTestCase(0x10) } { includeSharedExamples() }
    context(target + " = $99") { initTestCase(0x99) } { includeSharedExamples() }
  }

  private def executeSharedExamplesWithoutDecimalFlag(target: String, initTestCase: (Int) => Unit) {
    context(target + " = $00") { initTestCase(0x00) } { includeSharedExamples() }
    context(target + " = $01") { initTestCase(0x01) } { includeSharedExamples() }
    context(target + " = $7f") { initTestCase(0x7f) } { includeSharedExamples() }
    context(target + " = $80") { initTestCase(0x80) } { includeSharedExamples() }
    context(target + " = $ff") { initTestCase(0xff) } { includeSharedExamples() }
  }

  def applySharedExamples(sym: String, op: OpCode) {
    op match {
      case _: OpCode_IMM => {
        describe("immediate addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            it("uses 2 CPU cycles") { expect { operation }.toUseCycles(0x02) }

            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(0xc001)()))

            context("$C000 = " + sym + " #$XX") {} {
              executeSharedExamples("operation argument value at $C001", (opArg) => { assignOpArg(opArg) })
            }
          }
        }
      }
      case _: OpCode_ZP => {
        describe("zeropage addressing mode") {
          testOpCode(op) {
            it("advances PC by 2 bytes") { expect { operation }.toAdvancePC(0x02) }
            it("uses 3 CPU cycles") { expect { operation }.toUseCycles(0x03) }

            includeSharedExamples = () => includeExamples(sym, List[Any](CF, DF, AC(), () => memoryRead(zp)()))

            context("$C000 = " + sym + " $02") { zp = 0x02 } {
              executeSharedExamples("operation argument value at $02", (opArg) => { assignOpArg(zp, opArg) })
            }
          }
        }
      }
      case _: OpCode_ZPX => {
        describe("zeropage,x addressing mode") {
          testOpCode(op) {
            // TODO
          }
        }
      }
      case _: OpCode_ABS => {
        describe("absolute addressing mode") {
          testOpCode(op) {
            // TODO
          }
        }
      }
      case _: OpCode_ABSX => {
        describe("absolute,x addressing mode") {
          testOpCode(op) {
            // TODO
          }
        }
      }
      case _: OpCode_ABSY => {
        describe("absolute,y addressing mode") {
          testOpCode(op) {
            // TODO
          }
        }
      }
      case _: OpCode_INDX => {
        describe("(indirect,x) addressing mode") {
          testOpCode(op) {
            // TODO
          }
        }
      }
      case _: OpCode_INDY => {
        describe("(indirect),y addressing mode") {
          testOpCode(op) {
            // TODO
          }
        }
      }
    }
  }
}
