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
class Register(var AC: ByteVal, var XR: ByteVal, var YR: ByteVal, SR: ByteVal, var SP: ByteVal, var PC: Short) extends Status.Register {

  _SR = SR

  def status = _SR

  def status_=(value: ByteVal): Unit = { _SR = value }

  /** Stack operations */
  def push(memory: Memory, value: ByteVal): Unit = {
    memory.write((0x0100 + SP).toShort, value)
    SP -= 1
  }

  def pop(memory: Memory) = {
    SP += 1
    memory.read((0x0100 + SP).toShort)
  }

  /** Program counter halves */
  private def pcHalves = Util.word2Nibbles(PC)

  def advancePC(offset: Int): Unit = { PC = (PC + offset).toShort }

  def setPC(address: Int): Unit = { PC = address.toShort }
}

/** Factory for [[com.github.pawelkrol.CPU6502.Register]] instances */
object Register {

  /** Creates a new set of empty registers */
  def apply() = new Register(AC = 0x00, XR = 0x00, YR = 0x00, SR = 0x20, SP = 0xff, PC = 0x0000)

  /** Creates a new set of empty registers with status register initialized with an arbitrary byte */
  def apply(SR: ByteVal) = new Register(AC = 0x00, XR = 0x00, YR = 0x00, SR, SP = 0xff, PC = 0x0000)

  /** Creates a new set of empty registers with program counter initialized with an arbitrary byte */
  def apply(PC: Short) = new Register(AC = 0x00, XR = 0x00, YR = 0x00, SR = 0x20, SP = 0xff, PC)

  /** Creates a new set of arbitrarily initialized registers */
  def apply(AC: ByteVal, XR: ByteVal, YR: ByteVal, SR: ByteVal, SP: ByteVal, PC: Int) = new Register(AC, XR, YR, SR, SP, PC.toShort)
}
