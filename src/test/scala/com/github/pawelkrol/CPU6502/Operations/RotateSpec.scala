package com.github.pawelkrol.CPU6502
package Operations

trait RotateSpec extends FunSharedExamples {

  protected def sharedExampleArguments(opCode: OpCode) = opCode match {
    case _: OpCode_AC => () => List[Any](CF, () => AC())
    case _: OpCode_ZP => () => List[Any](CF, () => memoryRead(zp)())
    case _: OpCode_ZPX => () => List[Any](CF, () => memoryRead(zp + xr)())
    case _: OpCode_ABS => () => List[Any](CF, () => memoryRead(addr)())
    case _: OpCode_ABSX => () => List[Any](CF, () => memoryRead(addr + xr)())
  }

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit): Unit = {
    context("with carry flag") { CF = true } {
      executeSharedExamplesWithFlags(target, initTestCase)
    }

    context("without carry flag") { CF = false } {
      executeSharedExamplesWithFlags(target, initTestCase)
    }
  }

  private def executeSharedExamplesWithFlags(target: String, initTestCase: (Int) => Unit): Unit = {
    context(target + " = $00") { initTestCase(0x00) } { includeSharedExamples() }
    context(target + " = $01") { initTestCase(0x01) } { includeSharedExamples() }
    context(target + " = $02") { initTestCase(0x02) } { includeSharedExamples() }
    context(target + " = $40") { initTestCase(0x40) } { includeSharedExamples() }
    context(target + " = $80") { initTestCase(0x80) } { includeSharedExamples() }
  }
}
