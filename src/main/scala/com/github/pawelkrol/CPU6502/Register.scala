package com.github.pawelkrol.CPU6502

import Status._

/** Registers
 *
 * @constructor Create a new set of registers
 * @param AC Accumulator
 * @param XR X register
 * @param YR Y register
 * @param SR Status register
 * @param SP Stack pointer
 * @param PC Program counter
 */
class Register(var AC: ByteVal, var XR: ByteVal, var YR: ByteVal, SR: ByteVal, SP: ByteVal, var PC: Short) extends Status.Register {

  _SR = SR
  private var _SP = SP

  /** Stack operations */
  private def push(memory: Memory, value: ByteVal) {
    memory.write((0x0100 + _SP).toShort, value)
    _SP -= 1
  }

  private def pop(memory: Memory) = {
    _SP += 1
    memory.read((0x0100 + _SP).toShort)
  }

  /** Program counter halves */
  private def pcHalves = Util.word2Nibbles(PC)

  def advancePC(offset: Int) { PC = (PC + offset).toShort }
}

/** Factory for [[com.github.pawelkrol.CPU6502.Register]] instances */
object Register {

  /** Creates a new set of empty registers */
  def apply() = new Register(AC = 0x00, XR = 0x00, YR = 0x00, SR = 0x00, SP = 0x00, PC = 0x00)

  /** Creates a new set of empty registers with status register initialized with an arbitrary byte */
  def apply(SR: ByteVal) = new Register(AC = 0x00, XR = 0x00, YR = 0x00, SR, SP = 0x00, PC = 0x00)

  /** Creates a new set of empty registers with program counter initialized with an arbitrary byte */
  def apply(PC: Short) = new Register(AC = 0x00, XR = 0x00, YR = 0x00, SR = 0x00, SP = 0x00, PC)

  /** Creates a new set of arbitrarily initialized registers */
  def apply(AC: ByteVal, XR: ByteVal, YR: ByteVal, SR: ByteVal, SP: ByteVal, PC: Int) = new Register(AC, XR, YR, SR, SP, PC.toShort)
}
