package com.github.pawelkrol.CPU6502
package Operations

class ShiftLeftSpec extends RotateSpec {

  cycleCount = Map[OpCode, Int](
    OpCode_ASL_AC -> 2,
    OpCode_ASL_ZP -> 5,
    OpCode_ASL_ZPX -> 6,
    OpCode_ASL_ABS -> 6,
    OpCode_ASL_ABSX -> 7
  )

  protected def setupSharedExamples: Unit = {
    sharedExamples("ASL", (args) => {
      val carry: Boolean = args(0).asInstanceOf[Boolean]
      val fetchValue: () => Int = args(1).asInstanceOf[() => Int]

      val initValue = fetchValue()

      val message = "(value = $%02x, CF = %s) => ".format(initValue, carry)

      initValue match {
        case 0x00 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x00) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x01 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x02) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x02 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x04) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x40 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x80) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
        }
        case 0x80 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x00) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
      }
    })
  }

  describe("shift left one bit") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("ASL", opCode))
  }
}
