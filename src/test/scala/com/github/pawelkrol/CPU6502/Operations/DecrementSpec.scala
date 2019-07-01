package com.github.pawelkrol.CPU6502
package Operations

class DecrementSpec extends UnarySpec {

  cycleCount = Map[OpCode, Int](
    OpCode_DEC_ZP -> 5,
    OpCode_DEC_ZPX -> 6,
    OpCode_DEC_ABS -> 6,
    OpCode_DEC_ABSX -> 7
  )

  protected def setupSharedExamples: Unit = {
    sharedExamples("DEC", (args) => {
      val fetchValue: () => Int = args(0).asInstanceOf[() => Int]

      val initValue = fetchValue()

      val message = "(operand value = $%02x) => ".format(initValue)

      initValue match {
        case 0x00 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0xff) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
        }
        case 0x01 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x00) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x7f => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x7e) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x80 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x7f) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0xff => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0xfe) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
        }
      }
    })
  }

  describe("decrement memory by one") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("DEC", opCode))
  }
}
