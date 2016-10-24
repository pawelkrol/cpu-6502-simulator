package com.github.pawelkrol.CPU6502
package Operations

trait StoreSpec extends FunSharedExamples {

  protected def sharedExampleArguments(address: Short) = List[Any](() => address.toInt, () => memoryRead(address)())

  protected def sharedExampleArguments(opCode: OpCode) = opCode match {
    case _: OpCode_ZP => () =>
      sharedExampleArguments(zp)
    case _: OpCode_ZPX => () =>
      sharedExampleArguments(zp + xr)
    case _: OpCode_ABS => () =>
      sharedExampleArguments(addr)
    case _: OpCode_ABSX => () =>
      sharedExampleArguments((addr + xr).toShort)
    case _: OpCode_ABSY => () =>
      sharedExampleArguments((addr + yr).toShort)
    case _: OpCode_INDX => () =>
      sharedExampleArguments(Util.nibbles2Word(memoryRead(zp + xr)(), memoryRead(zp + xr + 1)()))
    case _: OpCode_INDY => () =>
      sharedExampleArguments((Util.nibbles2Word(memoryRead(zp)(), memoryRead(zp + 1)()) + yr).toShort)
  }

  protected def initStoreTestCase(ac: ByteVal, initTestCase: (Int) => Unit) {
    AC = ac
    initTestCase(0xff) // irrelevant value in a target memory address to be overwritten after storing AC in it
  }

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit) {
    context("AC = $00") { initStoreTestCase(0x00, initTestCase) } { includeSharedExamples() }
    context("AC = $01") { initStoreTestCase(0x01, initTestCase) } { includeSharedExamples() }
    context("AC = $80") { initStoreTestCase(0x80, initTestCase) } { includeSharedExamples() }
    context("AC = $FF") { initStoreTestCase(0xff, initTestCase) } { includeSharedExamples() }
  }
}
