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

  context("irq request") { subject { core.executeInstruction } } {
    it("generates an IRQ") {
      // TODO
    }
  }

  context("nmi request") {} {
    it("generates an NMI") {
      // TODO
    }
  }

  context("execute instruction") {} {
    it("executes one CPU instruction") {
      // TODO
    }

    it("returns the number of clock cycles for the executed instruction") {
      // TODO
    }
  }

  context("get cycles") {} {
    it("returns the total number of clock cycles executed") {
      // TODO
    }
  }
}
