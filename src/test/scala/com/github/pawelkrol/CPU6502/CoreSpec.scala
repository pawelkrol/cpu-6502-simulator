package com.github.pawelkrol.CPU6502

class CoreSpec extends FunFunSpec {

  private var memory: Memory = _
  private var register: Register = _
  private var core: Core = _

  before {
    memory = Memory()
    register = Register(0x00, 0x00, 0x00, 0x20, 0xf9, 0xc000)
    core = Core(memory, register)
  }

  context("reset") { subject { core.reset } } {
    it("generates a CPU reset") {
      expect { subject }.toChange { core.haveIRQRequest }.to(false)
      expect { subject }.toChange { core.haveNMIRequest }.to(false)
      expect { subject }.toChange { register.status }.to(0x00)
      expect { subject }.toChange { register.PC }.to(0xffff.toShort)
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

  context("execute instruction") { subject { core.executeInstruction } } {
    describe("when having an NMI request") {
      // TODO
    }

    describe("when having an IRQ request") {
      describe("with interrupt flag") {
        // TODO
      }

      describe("without interrupt flag") {
        // TODO
      }
    }

    context("LDA #$00") { memory.write(0xc000, 0xa9, 0x00) } {
      it("executes one CPU instruction") {
        // TODO
      }

      describe("cycle count") {
        it("returns the number of clock cycles for the executed instruction") {
          // TODO
        }
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
