package com.github.pawelkrol.CPU6502
package Operations

class RotateLeftSpec extends RotateSpec {

  cycleCount = Map[OpCode, Int](
    OpCode_ROL_AC -> 2,
    OpCode_ROL_ZP -> 5,
    OpCode_ROL_ZPX -> 6,
    OpCode_ROL_ABS -> 6,
    OpCode_ROL_ABSX -> 7
  )

  protected def setupSharedExamples: Unit = {
    sharedExamples("ROL", (args) => {
      val carry: Boolean = args(0).asInstanceOf[Boolean]
      val fetchValue: () => Int = args(1).asInstanceOf[() => Int]

      val initValue = fetchValue()

      val message = "(value = $%02x, CF = %s) => ".format(initValue, carry)

      if (carry) {
        initValue match {
          case 0x00 => {
            it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x01) }
            it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
            it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
            it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
          }
          case 0x01 => {
            it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x03) }
            it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
            it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
            it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
          }
          case 0x02 => {
            it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x05) }
            it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
            it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
            it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
          }
          case 0x40 => {
            it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x81) }
            it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
            it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
            it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
          }
          case 0x80 => {
            it(message + "result") { expect { operation }.toChange { fetchValue() }.to(0x01) }
            it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
            it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
            it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
          }
        }
      }
      else {
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
      }
    })
  }

  describe("rotate one bit left") {
    cycleCount.keys.foreach((opCode) => applySharedExamples("ROL", opCode))
  }
}
