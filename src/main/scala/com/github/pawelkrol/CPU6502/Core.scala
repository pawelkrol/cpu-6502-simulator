package com.github.pawelkrol.CPU6502

case class Core(memory: Memory, register: Register) extends Operation(memory, register) {

  /** IRQ request active */
  var haveIRQRequest = false

  /** NMI request active */
  var haveNMIRequest = false

  /** Generate a CPU RESET */
  def reset {
    haveIRQRequest = false
    haveNMIRequest = false
    register.status = 0x00
    register.PC = memory.get_val_from_addr(0xfffc.toShort)
  }

  /** Generate an IRQ */
  def requestIRQ {
    haveIRQRequest = true
  }

  /** Generate an NMI */
  def requestNMI {
    haveNMIRequest = true
  }

  /** Execute one CPU instruction */
  def executeInstruction {
    val opCode = OpCode(memory.read(register.PC))
    eval(opCode)
  }

  /** Execute n CPU instructions */
  def executeInstructions(n: Int) {
    1 to n foreach { _ => executeInstruction }
  }
}

object Core {

  /** Initializes a new CPU core */
  def apply() = new Core(Memory(), Register())

  /** Initializes a new CPU core with program counter initialized with an arbitrary byte */
  def apply(PC: Int) = new Core(Memory(), Register(PC.toShort))
}
