package com.github.pawelkrol.CPU6502
package Operations

class ShiftRightSpec extends RotateSpec {

  protected def setupSharedExamples {
    sharedExamples("LSR", (args) => {
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
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x00) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x02 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x01) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x40 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x20) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
        case 0x80 => {
          it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x40) }
          it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
          it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
          it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
        }
      }
    })
  }

  describe("shift right one bit") {
    applySharedExamples("LSR", OpCode_LSR_AC)
    applySharedExamples("LSR", OpCode_LSR_ZP)
    applySharedExamples("LSR", OpCode_LSR_ZPX)
    applySharedExamples("LSR", OpCode_LSR_ABS)
    applySharedExamples("LSR", OpCode_LSR_ABSX)
  }
}
