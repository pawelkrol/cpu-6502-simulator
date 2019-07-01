package com.github.pawelkrol.CPU6502
package Operations

trait UnarySpec extends FunSharedExamples {

  protected def sharedExampleArguments(opCode: OpCode) = opCode match {
    case _: OpCode_ZP => () => List[Any](() => memoryRead(zp)())
    case _: OpCode_ZPX => () => List[Any](() => memoryRead(zp + xr)())
    case _: OpCode_ABS => () => List[Any](() => memoryRead(addr)())
    case _: OpCode_ABSX => () => List[Any](() => memoryRead(addr + xr)())
  }

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit): Unit = {
    context(target + " = $00") { initTestCase(0x00) } { includeSharedExamples() }
    context(target + " = $01") { initTestCase(0x01) } { includeSharedExamples() }
    context(target + " = $7F") { initTestCase(0x7f) } { includeSharedExamples() }
    context(target + " = $80") { initTestCase(0x80) } { includeSharedExamples() }
    context(target + " = $FF") { initTestCase(0xff) } { includeSharedExamples() }
  }
}
