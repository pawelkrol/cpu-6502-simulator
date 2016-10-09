package com.github.pawelkrol.CPU6502
package Operations

class RotateLeftSpec extends FunOperationsSpec {

  private var zp: ByteVal = _

  private var xr: ByteVal = _

  private var addr: Short = _

  private def setupAbsOpArg(address: Short, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ value): _*) }

  private def setupAbsXOpArg(address: Short, xr: ByteVal, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ xr :+ value): _*) }

  describe("rotate one bit left") {
    describe("accumulator addressing mode") {
      testOpCode(OpCode_ROL_AC) {
        it { expect { operation }.toAdvancePC(0x01) }
        it { expect { operation }.toUseCycles(0x02) }

        context("with carry flag") { CF = true } {
          context("ROL A") { assignOpArg() } {
            context("AC = $00") { AC = 0x00; SF = false; ZF = true } {
              it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
              it { expect { operation }.toChange { CF }.from(true).to(false) }
              it { expect { operation }.toChange { ZF }.from(true).to(false) }
              it { expect { operation }.notToChange { SF } }
            }

            context("AC = $01") { AC = 0x01; SF = false; ZF = false } {
              it { expect { operation }.toChange { AC }.from(0x01).to(0x03) }
              it { expect { operation }.toChange { CF }.from(true).to(false) }
              it { expect { operation }.notToChange { ZF } }
              it { expect { operation }.notToChange { SF } }
            }

            context("AC = $40") { AC = 0x40; SF = false; ZF = false } {
              it { expect { operation }.toChange { AC }.from(0x40).to(0x81) }
              it { expect { operation }.toChange { CF }.from(true).to(false) }
              it { expect { operation }.notToChange { ZF } }
              it { expect { operation }.toChange { SF }.from(false).to(true) }
            }

            context("AC = $80") { AC = 0x80; SF = true; ZF = false } {
              it { expect { operation }.toChange { AC }.from(0x80).to(0x01) }
              it { expect { operation }.notToChange { CF } }
              it { expect { operation }.notToChange { ZF } }
              it { expect { operation }.toChange { SF }.from(true).to(false) }
            }
          }
        }

        context("without carry flag") { CF = false } {
          context("ROL A") { assignOpArg() } {
            context("AC = $00") { AC = 0x00; SF = false; ZF = true } {
              it { expect { operation }.notToChange { AC } }
              it { expect { operation }.notToChange { CF } }
              it { expect { operation }.notToChange { ZF } }
              it { expect { operation }.notToChange { SF } }
            }

            context("AC = $01") { AC = 0x01; SF = false; ZF = false } {
              it { expect { operation }.toChange { AC }.from(0x01).to(0x02) }
              it { expect { operation }.notToChange { CF } }
              it { expect { operation }.notToChange { ZF } }
              it { expect { operation }.notToChange { SF } }
            }

            context("AC = $40") { AC = 0x40; SF = false; ZF = false } {
              it { expect { operation }.toChange { AC }.from(0x40).to(0x80) }
              it { expect { operation }.notToChange { CF } }
              it { expect { operation }.notToChange { ZF } }
              it { expect { operation }.toChange { SF }.from(false).to(true) }
            }

            context("AC = $80") { AC = 0x80; SF = true; ZF = false } {
              it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
              it { expect { operation }.toChange { CF }.from(false).to(true) }
              it { expect { operation }.toChange { ZF }.from(false).to(true) }
              it { expect { operation }.toChange { SF }.from(true).to(false) }
            }
          }
        }
      }
    }

    describe("zeropage addressing mode") {
      testOpCode(OpCode_ROL_ZP) {
        it { expect { operation }.toAdvancePC(0x02) }
        it { expect { operation }.toUseCycles(0x05) }

        context("with carry flag") { CF = true } {
          context("ROL $02") { zp = 0x02 } {
            context("$0002 = $00") { assignOpArg(zp, 0x00) } {
              it { expect { operation }.toChange { memoryRead(zp) }.from(0x00).to(0x01) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$0002 = $01") { assignOpArg(zp, 0x01) } {
              it { expect { operation }.toChange { memoryRead(zp) }.from(0x01).to(0x03) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$0002 = $40") { assignOpArg(zp, 0x40) } {
              it { expect { operation }.toChange { memoryRead(zp) }.from(0x40).to(0x81) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(true) }
            }

            context("$0002 = $80") { assignOpArg(zp, 0x80) } {
              it { expect { operation }.toChange { memoryRead(zp) }.from(0x80).to(0x01) }
              it { expect { operation }.toChange { CF }.to(true) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }
          }
        }

        context("without carry flag") { CF = false } {
          context("ROL $02") { zp = 0x02 } {
            context("$0002 = $00") { assignOpArg(zp, 0x00) } {
              it { expect { operation }.notToChange { memoryRead(zp) } }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(true) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$0002 = $01") { assignOpArg(zp, 0x01) } {
              it { expect { operation }.toChange { memoryRead(zp) }.from(0x01).to(0x02) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$0002 = $40") { assignOpArg(zp, 0x40) } {
              it { expect { operation }.toChange { memoryRead(zp) }.from(0x40).to(0x80) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(true) }
            }

            context("$0002 = $80") { assignOpArg(zp, 0x80) } {
              it { expect { operation }.toChange { memoryRead(zp) }.from(0x80).to(0x00) }
              it { expect { operation }.toChange { CF }.to(true) }
              it { expect { operation }.toChange { ZF }.to(true) }
              it { expect { operation }.toChange { SF }.to(false) }
            }
          }
        }
      }
    }

    describe("zeropage,x addressing mode") {
      testOpCode(OpCode_ROL_ZPX) {
        it { expect { operation }.toAdvancePC(0x02) }
        it { expect { operation }.toUseCycles(0x06) }

        context("with carry flag") { CF = true } {
          context("XR = $02") { XR = 0x02 } {
            context("ROL $02,X") { zp = 0x02; xr = 0x02 } {
              context("$0004 = $00") { assignOpArg(zp, xr, 0x00) } {
                it { expect { operation }.toChange { memoryRead(zp + xr) }.from(0x00).to(0x01) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$0004 = $01") { assignOpArg(zp, xr, 0x01) } {
                it { expect { operation }.toChange { memoryRead(zp + xr) }.from(0x01).to(0x03) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$0004 = $40") { assignOpArg(zp, xr, 0x40) } {
                it { expect { operation }.toChange { memoryRead(zp + xr) }.from(0x40).to(0x81) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(true) }
              }

              context("$0004 = $80") { assignOpArg(zp, xr, 0x80) } {
                it { expect { operation }.toChange { memoryRead(zp + xr) }.from(0x80).to(0x01) }
                it { expect { operation }.toChange { CF }.to(true) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }
            }
          }
        }

        context("without carry flag") { CF = false } {
          context("XR = $02") { XR = 0x02 } {
            context("ROL $02,X") { zp = 0x02; xr = 0x02 } {
              context("$0004 = $00") { assignOpArg(zp, xr, 0x00) } {
                it { expect { operation }.notToChange { memoryRead(zp + xr) } }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(true) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$0004 = $01") { assignOpArg(zp, xr, 0x01) } {
                it { expect { operation }.toChange { memoryRead(zp + xr) }.from(0x01).to(0x02) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$0004 = $40") { assignOpArg(zp, xr, 0x40) } {
                it { expect { operation }.toChange { memoryRead(zp + xr) }.from(0x40).to(0x80) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(true) }
              }

              context("$0004 = $80") { assignOpArg(zp, xr, 0x80) } {
                it { expect { operation }.toChange { memoryRead(zp + xr) }.from(0x80).to(0x00) }
                it { expect { operation }.toChange { CF }.to(true) }
                it { expect { operation }.toChange { ZF }.to(true) }
                it { expect { operation }.toChange { SF }.to(false) }
              }
            }
          }
        }
      }
    }

    describe("absolute addressing mode") {
      testOpCode(OpCode_ROL_ABS) {
        it { expect { operation }.toAdvancePC(0x03) }
        it { expect { operation }.toUseCycles(0x06) }

        context("with carry flag") { CF = true } {
          context("ROL $C800") { addr = 0xc800.toShort } {
            context("$C800 = $00") { setupAbsOpArg(addr, 0x00) } {
              it { expect { operation }.toChange { memoryRead(addr) }.from(0x00).to(0x01) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$C800 = $01") { setupAbsOpArg(addr, 0x01) } {
              it { expect { operation }.toChange { memoryRead(addr) }.from(0x01).to(0x03) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$C800 = $40") { setupAbsOpArg(addr, 0x40) } {
              it { expect { operation }.toChange { memoryRead(addr) }.from(0x40).to(0x81) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(true) }
            }

            context("$C800 = $80") { setupAbsOpArg(addr, 0x80) } {
              it { expect { operation }.toChange { memoryRead(addr) }.from(0x80).to(0x01) }
              it { expect { operation }.toChange { CF }.to(true) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }
          }
        }

        context("without carry flag") { CF = false } {
          context("ROL $C800") { addr = 0xc800.toShort } {
            context("$C800 = $00") { setupAbsOpArg(addr, 0x00) } {
              it { expect { operation }.notToChange { memoryRead(addr) } }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(true) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$C800 = $01") { setupAbsOpArg(addr, 0x01) } {
              it { expect { operation }.toChange { memoryRead(addr) }.from(0x01).to(0x02) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(false) }
            }

            context("$C800 = $40") { setupAbsOpArg(addr, 0x40) } {
              it { expect { operation }.toChange { memoryRead(addr) }.from(0x40).to(0x80) }
              it { expect { operation }.toChange { CF }.to(false) }
              it { expect { operation }.toChange { ZF }.to(false) }
              it { expect { operation }.toChange { SF }.to(true) }
            }

            context("$C800 = $80") { setupAbsOpArg(addr, 0x80) } {
              it { expect { operation }.toChange { memoryRead(addr) }.from(0x80).to(0x00) }
              it { expect { operation }.toChange { CF }.to(true) }
              it { expect { operation }.toChange { ZF }.to(true) }
              it { expect { operation }.toChange { SF }.to(false) }
            }
          }
        }
      }
    }

    describe("absolute,x addressing mode") {
      testOpCode(OpCode_ROL_ABSX) {
        it { expect { operation }.toAdvancePC(0x03) }
        it { expect { operation }.toUseCycles(0x07) }

        context("with carry flag") { CF = true } {
          context("XR = $02") { XR = 0x02 } {
            context("ROL $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
              context("$C802 = $00") { setupAbsXOpArg(addr, xr, 0x00) } {
                it { expect { operation }.toChange { memoryRead(addr + xr) }.from(0x00).to(0x01) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$C802 = $01") { setupAbsXOpArg(addr, xr, 0x01) } {
                it { expect { operation }.toChange { memoryRead(addr + xr) }.from(0x01).to(0x03) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$C802 = $40") { setupAbsXOpArg(addr, xr, 0x40) } {
                it { expect { operation }.toChange { memoryRead(addr + xr) }.from(0x40).to(0x81) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(true) }
              }

              context("$C802 = $80") { setupAbsXOpArg(addr, xr, 0x80) } {
                it { expect { operation }.toChange { memoryRead(addr + xr) }.from(0x80).to(0x01) }
                it { expect { operation }.toChange { CF }.to(true) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }
            }
          }
        }

        context("without carry flag") { CF = false } {
          context("XR = $02") { XR = 0x02 } {
            context("ROL $C800,X") { addr = 0xc800.toShort; xr = 0x02 } {
              context("$C802 = $00") { setupAbsXOpArg(addr, xr, 0x00) } {
                it { expect { operation }.notToChange { memoryRead(addr + xr) } }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(true) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$C802 = $01") { setupAbsXOpArg(addr, xr, 0x01) } {
                it { expect { operation }.toChange { memoryRead(addr + xr) }.from(0x01).to(0x02) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(false) }
              }

              context("$C802 = $40") { setupAbsXOpArg(addr, xr, 0x40) } {
                it { expect { operation }.toChange { memoryRead(addr + xr) }.from(0x40).to(0x80) }
                it { expect { operation }.toChange { CF }.to(false) }
                it { expect { operation }.toChange { ZF }.to(false) }
                it { expect { operation }.toChange { SF }.to(true) }
              }

              context("$C802 = $80") { setupAbsXOpArg(addr, xr, 0x80) } {
                it { expect { operation }.toChange { memoryRead(addr + xr) }.from(0x80).to(0x00) }
                it { expect { operation }.toChange { CF }.to(true) }
                it { expect { operation }.toChange { ZF }.to(true) }
                it { expect { operation }.toChange { SF }.to(false) }
              }
            }
          }
        }
      }
    }
  }
}
