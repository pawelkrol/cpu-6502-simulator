package com.github.pawelkrol.CPU6502
package Operations

trait StoreSpec extends FunSharedExamples {

  protected def storedSymbol: String

  protected def assignStoredValue(storedValue: ByteVal): Unit

  protected def sharedExampleArguments(address: Short) = List[Any](() => address.toInt, () => memoryRead(address)())

  protected def sharedExampleArguments(opCode: OpCode) = opCode match {
    case _: OpCode_ZP => () =>
      sharedExampleArguments(zp)
    case _: OpCode_ZPX => () =>
      sharedExampleArguments(zp + xr)
    case _: OpCode_ZPY => () =>
      sharedExampleArguments(zp + yr)
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

  protected def initStoreTestCase(storedValue: ByteVal, initTestCase: (Int) => Unit): Unit = {
    assignStoredValue(storedValue)
    initTestCase(0xff) // irrelevant value in a target memory address to be overwritten after storing AC in it
  }

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit): Unit = {
    val storedValues = Seq[ByteVal](0x00, 0x01, 0x80, 0xff)

    storedValues.foreach((storedValue) => {
      context("%s = $%02x".format(storedSymbol, storedValue())) { initStoreTestCase(storedValue, initTestCase) } {
        includeSharedExamples()
      }
    })
  }
}
