package com.github.pawelkrol.CPU6502
package Operations

class IncrementSpec extends UnarySpec {

  cycleCount = Map[OpCode, Int](
    OpCode_INC_ZP -> 5,
    OpCode_INC_ZPX -> 6,
    OpCode_INC_ABS -> 6,
    OpCode_INC_ABSX -> 7
  )

  protected def setupSharedExamples: Unit = {
    sharedExamples("INC", (args) => {
      val fetchValue: () => Int = args(0).asInstanceOf[() => Int]

      val initValue = fetchValue()

      val message = "(operand value = $%02x) => ".format(initValue)

      initValue match {
        case 0x00 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x01) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x01 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x02) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x7f => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x80) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
        }
        case 0x80 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x81) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
        }
        case 0xff => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x00) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
      }
    })
  }

  describe("increment memory by one") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("INC", opCode))
  }
}
