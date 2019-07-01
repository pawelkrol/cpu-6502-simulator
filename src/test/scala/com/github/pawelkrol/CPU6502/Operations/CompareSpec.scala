package com.github.pawelkrol.CPU6502
package Operations

trait CompareSpec extends FunSharedExamples {

  protected def comparedSymbol: String

  protected def opCodeSymbol: String

  protected def assignLoadedValue(loadedValue: ByteVal): Unit

  protected def fetchLoadedValue: () => ByteVal

  protected def sharedExampleArguments(opCode: OpCode) = () => List[() => Int](() => opCode match {
    case _: OpCode_IMM => PC + 1
    case _: OpCode_ZP => zp()
    case _: OpCode_ZPX => (zp + xr)()
    case _: OpCode_ABS => addr
    case _: OpCode_ABSX => addr + xr
    case _: OpCode_ABSY => addr + yr
    case _: OpCode_INDX => Util.nibbles2Word(memoryRead(zp + xr)(), memoryRead(zp + xr + 1)())
    case _: OpCode_INDY => Util.nibbles2Word(memoryRead(zp)(), memoryRead(zp + 1)()) + yr
  })

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit): Unit = {
    val loadedValues = Seq[ByteVal](0x00, 0x01, 0xff)
    val comparedValues = Seq[ByteVal](0x00, 0x01, 0xff)

    loadedValues.foreach((loadedValue) => {
      context("%s = $%02X".format(comparedSymbol, loadedValue())) { assignLoadedValue(loadedValue) } {
        it("does not modify loaded value") { expect { operation }.notToChange { fetchLoadedValue() } }

        comparedValues.foreach((comparedValue) => {
          context("compares with = $%02X".format(comparedValue())) { initTestCase(comparedValue()) } {
            includeSharedExamples()
          }
        })
      }
    })
  }

  protected def setupSharedExamples: Unit = {
    sharedExamples(opCodeSymbol, (args) => {
      val fetchAddress = args(0).asInstanceOf[() => Int]().toShort

      val comparedValue: Int = memoryRead(fetchAddress)()
      val loadedValue: Int = fetchLoadedValue()()

      val message = "$%04X = $%02X".format(fetchAddress, comparedValue)

      val expectedResults = Map(
        0x00 -> Map(
          0x00 -> (true, true, false),
          0x01 -> (false, false, true),
          0xff -> (false, false, false)
        ),
        0x01 -> Map(
          0x00 -> (true, false, false),
          0x01 -> (true, true, false),
          0xff -> (false, false, false)
        ),
        0xff -> Map(
          0x00 -> (true, false, true),
          0x01 -> (true, false, true),
          0xff -> (true, true, false)
        )
      )

      // AC/XR/YR = loadedValue, comparison against argument = comparedValue
      val (carryFlag, zeroFlag, signFlag) = expectedResults(loadedValue)(comparedValue)

      it(message + " (CF should be " + carryFlag + ")") { expect { operation }.toChange { CF }.to(carryFlag) }
      it(message + " (ZF should be " + zeroFlag + ")") { expect { operation }.toChange { ZF }.to(zeroFlag) }
      it(message + " (SF should be " + signFlag + ")") { expect { operation }.toChange { SF }.to(signFlag) }
    })
  }
}
