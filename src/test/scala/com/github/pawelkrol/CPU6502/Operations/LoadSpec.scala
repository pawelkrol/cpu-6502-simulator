package com.github.pawelkrol.CPU6502
package Operations

trait LoadSpec extends FunSharedExamples {

  protected def assertFlags(value: Int) {
    val signFlag = (value & 0x80) == 0x80
    val zeroFlag = value == 0x00
    it("ZF = " + zeroFlag) { expect { operation }.toChange { ZF }.to(zeroFlag) }
    it("SF = " + signFlag) { expect { operation }.toChange { SF }.to(signFlag) }
  }

  protected def sharedExampleArguments(opCode: OpCode) = opCode match {
    case _: OpCode_IMM => () => List[Any](memoryRead((PC + 1).toShort)())
    case _: OpCode_ZP => () => List[Any](memoryRead(zp)())
    case _: OpCode_ZPX => () => List[Any](memoryRead(zp + xr)())
    case _: OpCode_ZPY => () => List[Any](memoryRead(zp + yr)())
    case _: OpCode_ABS => () => List[Any](memoryRead(addr)())
    case _: OpCode_ABSX => () => List[Any](memoryRead(addr + xr)())
    case _: OpCode_ABSY => () => List[Any](memoryRead(addr + yr)())
  }

  protected def executeSharedExamples(target: String, initTestCase: (Int) => Unit) {
    val loadedValues = Seq[Int](0x00, 0x01, 0x80, 0xff)

    loadedValues.foreach((loadedValue) => {
      context("%s = $%02X".format(target, loadedValue)) { initTestCase(loadedValue) } {
        includeSharedExamples()
      }
    })
  }

  protected def assertPageBoundaryCycleCount(op: OpCode)(setupCallback: (Int, Int, () => Unit) => Unit) {
    describe("cycle count when page boundary is crossed") {
      val extraCycleCount = Map[Tuple2[Int, Int], Int](
        (0xc800, 0x00) -> 0,
        (0xc800, 0x01) -> 0,
        (0xc800, 0xff) -> 0,
        (0xc801, 0x00) -> 0,
        (0xc801, 0x01) -> 0,
        (0xc801, 0xff) -> 1,
        (0xc8ff, 0x00) -> 0,
        (0xc8ff, 0x01) -> 1,
        (0xc8ff, 0xff) -> 1
      )

      testOpCode(op) {
        extraCycleCount.foreach({ case (data, extraCycles) =>
          val (address, offset) = data
          setupCallback(address, offset, () => {
            assertCycleCount(cycleCount(op) + extraCycles)
          })
        })
      }
    }
  }
}
