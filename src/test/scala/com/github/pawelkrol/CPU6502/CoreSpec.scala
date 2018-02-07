package com.github.pawelkrol.CPU6502

class CoreSpec extends FunFunSpec {

  private var memory: Memory = _
  private var register: Register = _
  private var core: Core = _

  private def assertSetPC(handler: Int) {
    context("$%04X = $00, $%04X = $C8".format(handler, handler + 1)) { memory.write(handler, 0x00).write(handler + 1, 0xc8) } {
      it("sets PC to $C800 (as read from $%04X)".format(handler)) { expect { subject }.toChange { register.PC }.to(0xc800.toShort) }
    }
  }

  before {
    memory = SimpleMemory()
    register = Register(0x00, 0x00, 0x00, 0x20, 0xf9, 0xc000)
    core = Core(memory, register)
  }

  context("reset") { subject { core.reset } } {
    describe("generate a CPU reset") {
      it { expect { subject }.toChange { core.haveIRQRequest }.to(false) }
      it { expect { subject }.toChange { core.haveNMIRequest }.to(false) }
      it { expect { subject }.toChange { register.status }.to(0x00) }
      it { expect { subject }.toChange { register.PC }.to(0x0200.toShort) }
    }
  }

  context("irq request") { subject { core.requestIRQ } } {
    it("generates an IRQ") {
      expect { subject }.toChange { core.haveIRQRequest }.to(true)
    }
  }

  context("nmi request") { core.requestNMI } {
    it("generates an NMI") {
      expect { subject }.toChange { core.haveNMIRequest }.to(true)
    }
  }

  context("execute one CPU instruction") { subject { core.executeInstruction } } {
    context("when having an NMI request") { core.haveNMIRequest = true } {
      describe("handle an NMI request") {
        it { expect { subject }.notToChange { core.haveIRQRequest } }
        it { expect { subject }.toChange { core.haveNMIRequest }.to(false) }
      }

      it("uses 7 CPU cycles") { assert(core.executeInstruction == 0x07) }
      it("pushes 3 bytes onto stack") { expect { subject }.toChange { register.SP }.from(0xf9).to(0xf6) }

      it("pushes program counter to stack") {
        expect { subject }.toChange { memory.read(0x01f8, 0x02) }.to(Seq[ByteVal](0x00, 0xc0))
      }

      context("SR = %01100010") { register.setStatusFlag(Status.OF, true); register.setStatusFlag(Status.ZF, true) } {
        it("pushes processor status on stack") {
          expect { subject }.toChange { memory.read(0x01f7) }.to(0x62)
        }
      }

      it("sets interrupt flag") {
        expect { subject }.toChange { register.getStatusFlag(Status.IF) }.to(true)
      }

      assertSetPC(0xfffa)
    }

    context("when having an IRQ request") { core.haveIRQRequest = true } {
      context("$C000 = LDA #$10") { memory.write(0xc000, 0xa9, 0x10) } {
        context("with interrupt flag") { register.setStatusFlag(Status.IF, true) } {
          describe("execute normal instruction") {
            it { expect { subject }.notToChange { core.haveIRQRequest } }
            it { expect { subject }.notToChange { core.haveNMIRequest } }
          }

          it("does not push any bytes onto stack") { expect { subject }.notToChange { register.SP } }

          it("does not change interrupt flag") {
            expect { subject }.notToChange { register.getStatusFlag(Status.IF) }
          }
        }

        context("without interrupt flag") { register.setStatusFlag(Status.IF, false) } {
          describe("handle an IRQ request") {
            it { expect { subject }.toChange { core.haveIRQRequest }.to(false) }
            it { expect { subject }.notToChange { core.haveNMIRequest } }
          }

          it("uses 7 CPU cycles") { assert(core.executeInstruction == 0x07) }
          it("pushes 3 bytes onto stack") { expect { subject }.toChange { register.SP }.from(0xf9).to(0xf6) }

          it("pushes program counter to stack") {
            expect { subject }.toChange { memory.read(0x01f8, 0x02) }.to(Seq[ByteVal](0x00, 0xc0))
          }

          context("SR = %00111000") { register.setStatusFlag(Status.BF, true); register.setStatusFlag(Status.DF, true) } {
            it("pushes processor status on stack") {
              expect { subject }.toChange { memory.read(0x01f7) }.to(0x38)
            }
          }

          it("sets interrupt flag") {
            expect { subject }.toChange { register.getStatusFlag(Status.IF) }.to(true)
          }

          assertSetPC(0xfffe)
        }
      }
    }

    context("LDA #$80") { memory.write(0xc000, 0xa9, 0x80) } {
      describe("execute one CPU instruction") {
        it { expect { subject }.toChange { register.getStatusFlag(Status.ZF) }.to(false) }
        it { expect { subject }.toChange { register.getStatusFlag(Status.SF) }.to(true) }
        it { expect { subject }.toChange { register.AC() }.from(0x00).to(0x80) }
      }

      it("advances PC by 2 bytes") { expect { subject }.toChange { register.PC }.to(0xc002.toShort) }

      describe("cycle count") {
        it("returns the number of clock cycles for the executed instruction") {
          assert(core.executeInstruction == 0x02)
        }
      }
    }

    context("with a Commodore 64C memory representation") { core = Core(CommodoreMemory()) } {
      context("simulate power-up RESET entry (starting from a default Kernal vector $FCE2") { subject { core.reset } } {
        it { expect { subject }.toChange { core.register.PC }.to(0xfce2.toShort) }
        it { expect { subject; core.executeInstruction }.toChange { core.register.PC }.to(0xfce4.toShort) }
        it { expect { subject; core.executeInstruction }.toChange { core.register.XR() }.to(0xff) }
      }
    }
  }

  describe("get cycles") {
    context("LDA #$00; STA $D011") { memory.write(0xc000, 0xa9, 0x00, 0x8d, 0x11, 0xd0) } {
      it("returns the total number of clock cycles executed") {
        expect { core.executeInstructions(0x02) }.toChange { core.totalCycles }.to(0x06)
      }
    }

    context("LDY #$00; LDA ($FB),Y; STA ($FD),Y") { memory.write(0xc000, 0xa0, 0x00, 0xb1, 0xfb, 0x91, 0xfd) } {
      it("returns the total number of clock cycles executed") {
        expect { core.executeInstructions(0x03) }.toChange { core.totalCycles }.to(0x0d)
      }
    }
  }
}
