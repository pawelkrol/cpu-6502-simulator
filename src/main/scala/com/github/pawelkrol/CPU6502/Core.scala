package com.github.pawelkrol.CPU6502

case class Core(memory: Memory, register: Register) extends Operation(memory, register)

object Core {

  /** Initializes a new CPU core */
  def apply() = new Core(Memory(), Register())

  /** Initializes a new CPU core with program counter initialized with an arbitrary byte */
  def apply(PC: Int) = new Core(Memory(), Register(PC.toShort))
}